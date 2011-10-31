package com.inepex.ineFrame.shared.auth;

import net.customware.gwt.dispatch.shared.Action;

public class LoginAction implements Action<AuthStatusResultBase> {

	private String userName;
	private String password;
	private String captchaAnswer;

	public LoginAction() {
	}

	public LoginAction(String userName, String password, String captchaAnswer) {
		this.userName = userName;
		this.password = password;
		this.captchaAnswer=captchaAnswer;
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

	public String getCaptchaAnswer() {
		return captchaAnswer;
	}
	
	public void setCaptchaAnswer(String captchaAnswer) {
		this.captchaAnswer = captchaAnswer;
	}
}
