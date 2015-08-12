package com.inepex.ineFrame.server.auth;

import com.google.inject.servlet.SessionScoped;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

/**
 * USE ONLY in synchronized blocks!!!!!
 */
@SessionScoped
public class SessionScopedAuthStat {

    private Long userId = null;
    private AuthStatusResultBase authStatusResultBase = null;

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public void clearState() {
        setUserId(null);
        setAuthStatusResultBase(null);
    }

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public void setAuthStatusResultBase(AuthStatusResultBase authStatusResultBase) {
        this.authStatusResultBase = authStatusResultBase;
    }

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public AuthStatusResultBase getAuthStatusResultBase() {
        return authStatusResultBase;
    }

    @Override
    public String toString() {
        return "SessionScopedAuthStat [userId="
            + userId
            + ", authStatusResultBase="
            + authStatusResultBase
            + "]";
    }
}
