package com.inepex.ineFrame.server.auth;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import nl.captcha.Captcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public abstract class AbstractLoginHandler<U extends AuthUser, R extends AuthStatusResultBase>
    extends
    AbstractIneHandler<LoginAction, AuthStatusResultBase> {

    private static final Logger _logger = LoggerFactory.getLogger(AbstractLoginHandler.class);

    private final Provider<SessionScopedAuthStat> authStatProvider;
    private final Provider<LoginCaptchaInfo> captchaInfoProvider;
    private final Provider<HttpSession> sessionProvider;

    protected AbstractLoginHandler(
        Provider<SessionScopedAuthStat> authStat,
        Provider<HttpSession> sesionProvider,
        Provider<LoginCaptchaInfo> captchaInfoProvider) {
        this.authStatProvider = authStat;
        this.captchaInfoProvider = captchaInfoProvider;
        this.sessionProvider = sesionProvider;
    }

    protected void onLogin(U user) {}

    @Override
    protected AuthStatusResultBase doExecute(LoginAction action, ExecutionContext context)
        throws AuthenticationException,
        DispatchException {
        U user;

        LoginCaptchaInfo captchaInfo = captchaInfoProvider.get();
        synchronized (captchaInfo) {
            if (action.getGoogleLoginToken() == null
                && (action.getUserName() == null || action.getPassword() == null)) {
                captchaInfo.registerIncorrectAnswer();
                return new AuthStatusResultBase(captchaInfo.needCaptcha());
            }

            if (captchaInfo.needCaptcha()) {
                // incorrect request
                if (action.getCaptchaAnswer() == null
                    || sessionProvider.get().getAttribute(Captcha.NAME) == null
                    || !action.getCaptchaAnswer().equals(
                        ((Captcha) sessionProvider.get().getAttribute(Captcha.NAME)).getAnswer())) {
                    captchaInfo.registerIncorrectAnswer();
                    return new AuthStatusResultBase(captchaInfo.needCaptcha());
                }
            }

            user = findByUserNameAndPassword(
                action.getUserName(),
                action.getPassword(),
                action.isGoogleLogin(),
                action.getGoogleLoginToken());
            if (user == null) {
                // incorrect password
                captchaInfo.registerIncorrectAnswer();
                return new AuthStatusResultBase(captchaInfo.needCaptcha());
            } else {
                captchaInfo.registerCorrectAnswer();
            }
        }

        R result = createResultBase();
        setUserToSession(user, result, action.getUserName());

        // if the login was successful and the user wants to stay signed in
        if (result.isSuccess() && action.isNeedStaySignedIn()) {
            String UUIDString = UUID.randomUUID().toString();
            result.setUserEmail(user.getUserAuthString());
            result.setUserUUID(UUIDString);
            // set the UUID for the user
            setUserStaySignedInUUID(result.getUserId(), UUIDString);
        }

        _logger.debug("Login successful: {}", authStatProvider.get());
        return result;
    }

    public void setUserToSession(U user, R result, String email) {
        result.setSuccess(true);
        result.setDisplayName(user.getDisplayName());
        result.setRoles(user.getAllowedRoles());
        result.setUserId(user.getUserId());
        result.setUserEmail(email);
        result.setUserPropJsons(user.getUserJsonProps());

        mapAdditional(user, result);
        onLogin(user);

        SessionScopedAuthStat authStat = authStatProvider.get();
        synchronized (authStat) {
            authStat.clearState();
            authStat.setUserId(user.getUserId());
            authStat.setAuthStatusResultBase(result);
        }
    }

    protected abstract void mapAdditional(U user, R result);

    /**
     * 
     * @return - the selected authUser or null if password or userName is
     *         incorrect
     */
    protected abstract U findByUserNameAndPassword(
        String userAuthString,
        String password,
        boolean isGoogleLogin,
        String googleLoginToken)
        throws DispatchException;

    /**
     * @return an empty result base object
     */
    protected abstract R createResultBase();

    @Override
    public Class<LoginAction> getActionType() {
        return LoginAction.class;
    }

    // methods for the stay signed in logic
    // needs to be implemented in the derived classes, because of the user
    // handling
    protected abstract void setUserStaySignedInUUID(Long userId, String UUIDString);

    public abstract AuthUser checkSignedInUUIDForUserAndLogUserIntoIfCorrect(
        String userEmail,
        String userUUID,
        AuthStatusResultBase result);

    public abstract void refresh(AuthStatusResultBase result);

}
