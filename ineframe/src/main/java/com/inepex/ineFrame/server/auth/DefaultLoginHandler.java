package com.inepex.ineFrame.server.auth;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public class DefaultLoginHandler extends AbstractLoginHandler<AuthUser,AuthStatusResultBase> {

	@Inject
	protected DefaultLoginHandler(Provider<SessionScopedAuthStat> authStat,
			Provider<HttpSession> sesionProvider,
			Provider<SessionScopedCaptchaInfo> captchaInfoProvider) {
		super(authStat, sesionProvider, captchaInfoProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void mapAdditional(AuthUser user, AuthStatusResultBase result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AuthUser findByUserNameAndPassword(String userAuthString,
			String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthStatusResultBase createResultBase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setUserStaySignedInUUID(String userName, String UUIDString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AuthUser checkSignedInUUIDForUser(String userEmail, String userUUID,
			AuthStatusResultBase result) {
		// TODO Auto-generated method stub
		return null;
	}

}