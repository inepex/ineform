package com.inepex.ineFrame.shared.auth;

import net.customware.gwt.dispatch.shared.Action;

public class LoginAction implements Action<AuthStatusResultBase> {

	private String userName;
	private String password;
	private String captchaAnswer;
	private boolean needStaySignedIn;

	public LoginAction() {
	}

	public LoginAction(String userName, String password, String captchaAnswer) {
		this.userName = userName;
		this.password = password;
		this.captchaAnswer=captchaAnswer;
	}
	
	public LoginAction(String userName, String password, String captchaAnswer, boolean needStaySignedIn) {
		this.userName = userName;
		this.password = password;
		this.captchaAnswer=captchaAnswer;
		this.needStaySignedIn=needStaySignedIn;
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

	public boolean isNeedStaySignedIn() {
		return needStaySignedIn;
	}

	public void setNeedStaySignedIn(boolean needStaySignedIn) {
		this.needStaySignedIn = needStaySignedIn;
	}
}
