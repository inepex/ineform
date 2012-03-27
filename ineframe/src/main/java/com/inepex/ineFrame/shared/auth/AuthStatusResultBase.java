package com.inepex.ineFrame.shared.auth;

import java.util.Set;

import com.inepex.ineom.shared.dispatch.GenericResult;

public class AuthStatusResultBase extends GenericResult {

	//if login was unsuccessful
	protected boolean needCaptcha;
	
	//if login is succesful
	protected String displayName;
	protected Long userId = null;
	protected Set<String> roles = null;
	// these two variables are for the stay signed in functionality
	protected String userEmail = null;
	protected String userUUID = null;

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

	public AuthStatusResultBase(String displayName, Long userId, Set<String> roles, String userEmail, String userUUID) {
		setSuccess(true);
		this.displayName = displayName;
		this.userId = userId;
		this.roles = roles;
		this.userEmail=userEmail;
		this.userId=userId;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	@Override
	public String toString() {
		return "AuthStatusResultBase [displayName=" + displayName + ", userId="
				+ userId + ", userEmail=" + userEmail + ", userUUID="
				+ userUUID + "]";
	}
}
