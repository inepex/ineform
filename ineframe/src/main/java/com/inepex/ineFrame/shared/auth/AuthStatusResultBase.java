package com.inepex.ineFrame.shared.auth;

import java.util.Set;

import com.inepex.ineom.shared.dispatch.GenericResult;

public class AuthStatusResultBase extends GenericResult {

	public String firstName = null;
	public String lastName = null;
	public Long userId = null;
	public Set<String> roles = null;

	public AuthStatusResultBase() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Long getUserId() {
		return userId;
	}

	public Set<String> getRoles() {
		return roles;
	}
	
	
}
