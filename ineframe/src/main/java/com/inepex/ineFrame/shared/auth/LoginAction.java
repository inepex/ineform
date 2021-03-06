package com.inepex.ineFrame.shared.auth;

import net.customware.gwt.dispatch.shared.Action;

public class LoginAction implements Action<AuthStatusResultBase> {

    private String userName;
    private String password;
    private String captchaAnswer;
    private boolean needStaySignedIn;
    private boolean isGoogleLogin = false;
    private String googleLoginToken;
    private int loginProductType;

    public LoginAction() {}

    public LoginAction(
        String userName,
        String password,
        String captchaAnswer,
        int loginProductType) {
        this.userName = userName;
        this.password = password;
        this.captchaAnswer = captchaAnswer;
        this.loginProductType = loginProductType;
    }

    public LoginAction(
        String userName,
        String password,
        String captchaAnswer,
        boolean needStaySignedIn,
        int loginProductType) {
        this.userName = userName;
        this.password = password;
        this.captchaAnswer = captchaAnswer;
        this.needStaySignedIn = needStaySignedIn;
        this.loginProductType = loginProductType;
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

    public boolean isGoogleLogin() {
        return isGoogleLogin;
    }

    public void setGoogleLogin(boolean isGoogleLogin) {
        this.isGoogleLogin = isGoogleLogin;
    }

    public String getGoogleLoginToken() {
        return googleLoginToken;
    }

    public void setGoogleLoginToken(String googleLoginToken) {
        this.googleLoginToken = googleLoginToken;
    }

    public int getLoginProductType() {
        return loginProductType;
    }

    public void setLoginProductType(int loginProductType) {
        this.loginProductType = loginProductType;
    }
}
