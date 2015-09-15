package com.inepex.ineFrame.server.auth;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public class DefaultLoginHandler extends AbstractLoginHandler<AuthUser, AuthStatusResultBase> {

    @Inject
    protected DefaultLoginHandler(
        Provider<SessionScopedAuthStat> authStat,
        Provider<HttpSession> sesionProvider,
        Provider<LoginCaptchaInfo> captchaInfoProvider) {
        super(authStat, sesionProvider, captchaInfoProvider);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void mapAdditional(AuthUser user, AuthStatusResultBase result) {
        // TODO Auto-generated method stub

    }

    @Override
    protected AuthStatusResultBase createResultBase() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void setUserStaySignedInUUID(Long userId, String UUIDString) {
        // TODO Auto-generated method stub

    }

    @Override
    public AuthUser checkSignedInUUIDForUserAndLogUserIntoIfCorrect(
        String userEmail,
        String userUUID,
        AuthStatusResultBase result) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void refresh(AuthStatusResultBase result) {}

    @Override
    protected AuthUser findByUserNameAndPassword(
        String userAuthString,
        String password,
        boolean isGoogleLogin,
        String googleLoginToken) throws DispatchException {
        // TODO Auto-generated method stub
        return null;
    }

}
