package com.inepex.ineFrame.shared.auth;

import net.customware.gwt.dispatch.shared.Action;

public class LoginAction implements Action<AuthStatusResultBase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String userName;
	String password;

	public LoginAction() {
	}

	public LoginAction(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
