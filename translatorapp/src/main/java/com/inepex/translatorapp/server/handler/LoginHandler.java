package com.inepex.translatorapp.server.handler;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.util.StringUtil;
import com.inepex.ineFrame.server.auth.AbstractLoginHandler;
import com.inepex.ineFrame.server.auth.AuthUser;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.auth.SessionScopedCaptchaInfo;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.dao.UserDao;

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
		try {
			User u = userDao.findByEmail(userAuthString);
			
			if(u!=null && password!=null && StringUtil.hash(password).equals(u.getPassword()))
				return u;
			else
				return null;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
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
