package com.inepex.ineFrame.server.auth;

import com.google.inject.servlet.SessionScoped;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

/**
 * 
 * USE ONLY in synchronized blocks!!!!!
 * 
 */
@SessionScoped
public class SessionScopedAuthStat {
	
	private Long userId=null;
	private AuthStatusResultBase authStatusResultBase=null;
	
	public void clearState() {
		setUserId(null);
		setAuthStatusResultBase(null);
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setAuthStatusResultBase(
			AuthStatusResultBase authStatusResultBase) {
		this.authStatusResultBase = authStatusResultBase;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public AuthStatusResultBase getAuthStatusResultBase() {
		return authStatusResultBase;
	}
}
