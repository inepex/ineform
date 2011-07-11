package com.inepex.ineFrame.client.auth;

import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public class NoAuthManager implements AuthManager {

	@Override
	public void checkAuthStatus(AuthActionCallback callback) {
		throw new RuntimeException();
	}

	@Override
	public void doLogin(String userName, String password,
			AuthActionCallback callback) {
		throw new RuntimeException();
	}

	@Override
	public AuthStatusResultBase getLastAuthStatusResult() {
		throw new RuntimeException();
	}

	@Override
	public void doLogout(AuthActionCallback callback) {
		throw new RuntimeException();
	}

	@Override
	public boolean isUserLoggedIn() {
		throw new RuntimeException();
	}

	@Override
	public boolean doUserHaveAnyOfRoles(String... roles) {
		throw new RuntimeException();
	}

}
