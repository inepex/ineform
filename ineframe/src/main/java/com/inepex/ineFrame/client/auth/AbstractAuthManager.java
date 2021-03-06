package com.inepex.ineFrame.client.auth;

import java.util.Date;
import java.util.Set;

import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.UnsupportedActionException;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Provider;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.auth.LogoutAction;
import com.inepex.ineFrame.shared.util.date.DateHelper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.dispatch.GenericActionResult;

public abstract class AbstractAuthManager implements AuthManager {

    public static interface AuthActionCallback {
        void onAuthCheckDone(AuthStatusResultBase resul);
    }

    AuthStatusResultBase lastAuthStatusResult = null;

    final Provider<IneDispatch> dispatcher;
    protected final EventBus eventBus;
    private int loginProductTypeOrdinal = 0;

    public AbstractAuthManager(Provider<IneDispatch> dispatcher, EventBus eventBus) {
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
    }

    @Override
    public void setLoginProductType(int loginProductTypeOrdinal) {
        this.loginProductTypeOrdinal = loginProductTypeOrdinal;

    }

    @Override
    public void checkAuthStatus(final AuthActionCallback callback) {
        GetAuthStatusAction action;
        String userEmail = Cookies.getCookie(IFConsts.COOKIE_STAYSIGNEDINUSERNAME);
        String userUUID = Cookies.getCookie(IFConsts.COOKIE_STAYSIGNEDINUUID);
        if (userEmail != null && userUUID != null) {
            action = new GetAuthStatusAction(userEmail, userUUID);
        } else {
            action = new GetAuthStatusAction();
        }
        dispatcher.get().getDispatcher().execute(action, new AuthStatusResultCallback(callback));
    }

    @Override
    public void doLogin(
        String userName,
        String password,
        String captchaAnswer,
        AuthActionCallback callback) {
        LoginAction action;
        boolean needStaySignedIn = Boolean
            .parseBoolean(Cookies.getCookie(IFConsts.COOKIE_NEEDSTAYSIGNEDIN));
        if (needStaySignedIn) {
            action = new LoginAction(
                userName,
                password,
                captchaAnswer,
                true,
                loginProductTypeOrdinal);
        } else {
            action = new LoginAction(userName, password, captchaAnswer, loginProductTypeOrdinal);
        }
        dispatcher.get().getDispatcher().execute(action, new AuthStatusResultCallback(callback));
    }

    @Override
    public void doGoogleLogin(String googleLoginToken, AuthActionCallback callback) {
        LoginAction action = new LoginAction();
        boolean needStaySignedIn = Boolean
            .parseBoolean(Cookies.getCookie(IFConsts.COOKIE_NEEDSTAYSIGNEDIN));
        action.setNeedStaySignedIn(needStaySignedIn);
        action.setGoogleLogin(true);
        action.setGoogleLoginToken(googleLoginToken);
        dispatcher.get().getDispatcher().execute(action, new AuthStatusResultCallback(callback));
    }

    public class AuthStatusResultCallback implements AsyncCallback<AuthStatusResultBase> {

        final AuthActionCallback callback;

        public AuthStatusResultCallback(AuthActionCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onFailure(Throwable arg0) {
            if (arg0 instanceof UnsupportedActionException || arg0 instanceof ActionException) {
                Window.alert(arg0.getMessage());
            }

            lastAuthStatusResult = null;
            callback.onAuthCheckDone(null);
        }

        @Override
        public void onSuccess(AuthStatusResultBase result) {
            lastAuthStatusResult = result;

            // here we set the cookies for the stay signed in functionality (if
            // set in the result)
            if (result != null && result.getUserUUID() != null && result.getUserEmail() != null) {
                Cookies.setCookie(
                    IFConsts.COOKIE_STAYSIGNEDINUUID,
                    result.getUserUUID(),
                    new Date(System.currentTimeMillis() + DateHelper.dayInMs * 30));
                Cookies.setCookie(
                    IFConsts.COOKIE_STAYSIGNEDINUSERNAME,
                    result.getUserEmail(),
                    new Date(System.currentTimeMillis() + DateHelper.dayInMs * 30));
            }

            callback.onAuthCheckDone(result);
        }
    }

    @Override
    public AuthStatusResultBase getLastAuthStatusResult() {
        return lastAuthStatusResult;
    }

    @Override
    public void doLogout(AuthActionCallback callback) {
        LogoutAction action = new LogoutAction();
        dispatcher.get().getDispatcher().execute(action, new LogoutCallback(callback));
    }

    class LogoutCallback implements AsyncCallback<GenericActionResult> {
        final AuthActionCallback callback;

        public LogoutCallback(AuthActionCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onFailure(Throwable arg0) {
            if (arg0 instanceof UnsupportedActionException) {
                Window.alert(arg0.getMessage());
            }

            lastAuthStatusResult = null;
            callback.onAuthCheckDone(null);
        }

        @Override
        public void onSuccess(GenericActionResult arg0) {
            lastAuthStatusResult = null;
            eventBus.fireEvent(new UserLoggedOutEvent());
            callback.onAuthCheckDone(null);
        }
    }

    @Override
    public boolean isUserLoggedIn() {
        return (!(lastAuthStatusResult == null || lastAuthStatusResult.getUserId() == null));
    }

    @Override
    public boolean doUserHaveAnyOfRoles(String... roles) {
        if (roles.length == 0)
            return true;

        if (getLastAuthStatusResult() == null || getLastAuthStatusResult().getRoles() == null)
            return false;

        Set<String> usersRoles = getLastAuthStatusResult().getRoles();
        for (String role : roles) {
            for (String usersRole : usersRoles) {
                if (role.equals(usersRole))
                    return true;
            }
        }
        return false;
    }

}
