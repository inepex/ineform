package com.inepex.example.ContactManager.server.handler;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.dao.UserDao;
import com.inepex.ineFrame.server.auth.AbstractLoginHandler;
import com.inepex.ineFrame.server.auth.AuthUser;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.auth.SessionScopedCaptchaInfo;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

@Singleton
public class LoginHandler extends AbstractLoginHandler<AuthUser, AuthStatusResultBase>{

	private final UserDao userDao;
	
	@Inject
	LoginHandler(Provider<SessionScopedAuthStat> authStat,
			Provider<HttpSession> sesionProvider,
			Provider<SessionScopedCaptchaInfo> captchaInfoProvider, UserDao userDao) {
		super(authStat, sesionProvider, captchaInfoProvider);
		
		this.userDao=userDao;
	}
	

	@Override
	protected User findByUserNameAndPassword(String userAuthString, String password) {
		User u = userDao.findByEmail(userAuthString);		
		if(u!=null && password!=null && password.equals(u.getPassword()))
			return u;
		else
			return null;
	}

	@Override
	protected void mapAdditional(AuthUser user, AuthStatusResultBase result) {
		//nothing to do
	}
	
	@Override
	protected AuthStatusResultBase createResultBase() {
		return new AuthStatusResultBase();
	}


	@Override
	protected void setUserStaySignedInUUID(Long userId, String UUIDString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AuthUser checkSignedInUUIDForUserAndLogUserIntoIfCorrect(
			String userEmail, String userUUID, AuthStatusResultBase result) {
		// TODO Auto-generated method stub
		return null;
	}

}
