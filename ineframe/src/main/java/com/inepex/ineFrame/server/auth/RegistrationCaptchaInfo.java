package com.inepex.ineFrame.server.auth;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class RegistrationCaptchaInfo {

    public static final int MAX_REGISTRATION_WITHOUT_CAPTCHA = 10;

    private int registrationCount = 0;

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public void registerRegistration() {
        registrationCount++;
    }

    /**
     * USE ONLY in synchronized blocks!!!!!
     */
    public boolean needCaptcha() {
        return registrationCount >= MAX_REGISTRATION_WITHOUT_CAPTCHA;
    }
}
