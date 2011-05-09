package com.inepex.ineFrame.client.auth;

import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.auth.LogoutAction;
import com.inepex.ineFrame.shared.dispatch.GenericResult;

public abstract class AbstractAuthManager<A extends AuthStatusResultBase> implements AuthManager<A> {

	public static interface AuthActionCallback {
		void onAuthCheckDone();
	}

	A lastAuthStatusResult = null;

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
			lastAuthStatusResult = null;
			callback.onAuthCheckDone();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(AuthStatusResultBase result) {
			try {
				lastAuthStatusResult = (A) result;
			} catch (Exception e) {
				Window.alert("Wrong AuthStatusHandler bound on server side!");
			}
			callback.onAuthCheckDone();
		}
	}

	@Override
	public A getLastAuthStatusResult() {
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
		return (!(lastAuthStatusResult == null || lastAuthStatusResult.userId == null));
	}
	
	@Override
	public boolean doUserHaveAnyOfRoles(String... roles) {
		if (getLastAuthStatusResult() == null || getLastAuthStatusResult().roles == null)
			return false;
		
		Set<String> usersRoles = getLastAuthStatusResult().roles;
		
		for (String role : roles) {
			for (String usersRole : usersRoles) {
				if (role.equals(usersRole))
					return true;
			}
		}
		return false;
	}
	
}
