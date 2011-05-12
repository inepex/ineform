package com.inepex.ineFrame.client.auth;

import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public interface AuthManager<A extends AuthStatusResultBase> {
	public abstract void checkAuthStatus(final AuthActionCallback callback);
	public abstract void doLogin(String userName, String password, AuthActionCallback callback);
	public abstract A getLastAuthStatusResult();
	public abstract void doLogout(AuthActionCallback callback);
	public abstract boolean isUserLoggedIn();
	public abstract boolean doUserHaveAnyOfRoles(String... roles);
}