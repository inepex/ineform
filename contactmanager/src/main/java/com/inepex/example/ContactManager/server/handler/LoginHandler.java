package com.inepex.example.ContactManager.server.handler;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.dao.UserDao;
import com.inepex.ineFrame.server.auth.AbstractLoginHandler;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

@Singleton
public class LoginHandler extends AbstractLoginHandler<User, AuthStatusResultBase>{
	
	private final UserDao userDao;
	
	@Inject
	protected LoginHandler(Provider<SessionScopedAuthStat> authStat, UserDao userDao) {
		super(authStat);
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
	protected void mapAdditional(User user, AuthStatusResultBase result) {
		//nothing to do
	}
	
	@Override
	protected AuthStatusResultBase createResultBase() {
		return new AuthStatusResultBase();
	}

}
