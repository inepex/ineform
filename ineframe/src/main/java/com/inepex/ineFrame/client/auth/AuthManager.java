package com.inepex.ineFrame.client.auth;

import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public interface AuthManager {
    public abstract void checkAuthStatus(final AuthActionCallback callback);

    public abstract void doLogin(
        String userName,
        String password,
        String captchaAnswer,
        AuthActionCallback callback);

    public abstract AuthStatusResultBase getLastAuthStatusResult();

    public abstract void doLogout(AuthActionCallback callback);

    public abstract boolean isUserLoggedIn();

    public abstract boolean doUserHaveAnyOfRoles(String... roles);

    public abstract void doGoogleLogin(String googleLoginToken, AuthActionCallback callback);

    public abstract void setLoginProductType(int typeOrdinal);
}