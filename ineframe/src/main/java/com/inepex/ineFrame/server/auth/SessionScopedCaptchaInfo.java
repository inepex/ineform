package com.inepex.ineFrame.server.auth;

import java.util.Date;

import com.google.inject.servlet.SessionScoped;
import com.inepex.ineFrame.shared.util.DateHelper;

/**
 * 
 * USE ONLY in synchronized blocks!!!!!
 * 
 */
@SessionScoped
public class SessionScopedCaptchaInfo {
	
	public static final int maxBadRequest=3;
	public static final long deterioration = DateHelper.minuteInMs*15;
	
	private int badLoginCount = 0;
	private long lastBadLogin = 0L;

	/**
	 * USE ONLY in synchronized blocks!!!!!
	 */
	public void registerCorrectAnswer() {
		badLoginCount=0;
		lastBadLogin=0L;
	}
	
	/**
	 * USE ONLY in synchronized blocks!!!!!
	 */
	public void registerIncorrectAnswer() {
		if(lastBadLogin+deterioration < new Date().getTime())
			badLoginCount=1;
		else 
			badLoginCount++;
		
		lastBadLogin=new Date().getTime();
	}
	
	/**
	 * USE ONLY in synchronized blocks!!!!!
	 */
	public boolean needCaptcha() {
		return 
			lastBadLogin+deterioration > new Date().getTime()
				&& badLoginCount >= maxBadRequest;
	}

}
