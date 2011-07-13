package com.inepex.ineFrame.client.auth;

import java.util.Set;

import net.customware.gwt.dispatch.shared.UnsupportedActionException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.auth.LogoutAction;
import com.inepex.ineom.shared.dispatch.GenericResult;

public abstract class AbstractAuthManager implements AuthManager {

	public static interface AuthActionCallback {
		void onAuthCheckDone();
	}

	AuthStatusResultBase lastAuthStatusResult = null;

	final IneDispatch dispatcher;
	
	public AbstractAuthManager(IneDispatch dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void checkAuthStatus(final AuthActionCallback callback) {
		GetAuthStatusAction action = new GetAuthStatusAction();
		dispatcher.getDispatcher().execute(action, new AuthStatusResultCallback(callback));
	}
	
	@Override
	public void doLogin(String userName, String password, AuthActionCallback callback) {
		LoginAction action = new LoginAction(userName, password);
		dispatcher.getDispatcher().execute(action, new AuthStatusResultCallback(callback));
	}
	
	class AuthStatusResultCallback implements AsyncCallback<AuthStatusResultBase>{
		
		final AuthActionCallback callback;
		
		public AuthStatusResultCallback(AuthActionCallback callback) {
			this.callback = callback;
		}
		
		@Override
		public void onFailure(Throwable arg0) {
			if(arg0 instanceof UnsupportedActionException)  {
				Window.alert(arg0.getMessage());
			}
			
			lastAuthStatusResult = null;
			callback.onAuthCheckDone();
		}

		@Override
		public void onSuccess(AuthStatusResultBase result) {
			lastAuthStatusResult = result;
			
			callback.onAuthCheckDone();
		}
	}

	@Override
	public AuthStatusResultBase getLastAuthStatusResult() {
		return lastAuthStatusResult;
	}	
	
	@Override
	public void doLogout(AuthActionCallback callback) {
		LogoutAction action = new LogoutAction();
		dispatcher.getDispatcher().execute(action, new LogoutCallback(callback));
	}
	
	class LogoutCallback implements AsyncCallback<GenericResult> {
		final AuthActionCallback callback;
		public LogoutCallback(AuthActionCallback callback) {
			this.callback = callback;
		}
		@Override
		public void onFailure(Throwable arg0) {
			if(arg0 instanceof UnsupportedActionException)  {
				Window.alert(arg0.getMessage());
			}
			
			lastAuthStatusResult = null;
			callback.onAuthCheckDone();
		}
		
		@Override
		public void onSuccess(GenericResult arg0) {
			lastAuthStatusResult = null;
			callback.onAuthCheckDone();
		}	
	}

	@Override
	public boolean isUserLoggedIn() {
		return (!(lastAuthStatusResult == null || lastAuthStatusResult.getUserId() == null));
	}
	
	@Override
	public boolean doUserHaveAnyOfRoles(String... roles) {
		if(roles.length==0)
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
