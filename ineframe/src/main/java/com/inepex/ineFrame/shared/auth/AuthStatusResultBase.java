package com.inepex.ineFrame.shared.auth;

import java.util.Set;

import com.inepex.ineom.shared.dispatch.GenericResult;

public class AuthStatusResultBase extends GenericResult {

	//if login was unsuccessful
	private boolean needCaptcha;
	
	//if login is succesful
	private String displayName;
	private Long userId = null;
	private Set<String> roles = null;

	public AuthStatusResultBase() {
	}
	
	public AuthStatusResultBase(boolean needCaptcha) {
		setSuccess(false);
		this.needCaptcha=needCaptcha;
	}

	public AuthStatusResultBase(String displayName, Long userId, Set<String> roles) {
		setSuccess(true);
		this.displayName = displayName;
		this.userId = userId;
		this.roles = roles;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getUserId() {
		return userId;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	public boolean isNeedCaptcha() {
		return needCaptcha;
	}
	
	public void setNeedCaptcha(boolean needCaptcha) {
		this.needCaptcha = needCaptcha;
	}
}
