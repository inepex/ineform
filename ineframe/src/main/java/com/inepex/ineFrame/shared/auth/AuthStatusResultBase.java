package com.inepex.ineFrame.shared.auth;

import java.util.Set;

import com.inepex.ineom.shared.dispatch.GenericResult;

public class AuthStatusResultBase extends GenericResult {

	private String firstName = null;
	private String lastName = null;
	private Long userId = null;
	private Set<String> roles = null;

	public AuthStatusResultBase() {
		super();
	}

	public AuthStatusResultBase(String firstName, String lastName, Long userId,
			Set<String> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.roles = roles;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
