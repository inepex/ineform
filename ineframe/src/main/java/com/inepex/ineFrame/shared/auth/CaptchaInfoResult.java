package com.inepex.ineFrame.shared.auth;

import com.inepex.ineom.shared.dispatch.GenericResult;

public class CaptchaInfoResult extends GenericResult {
	
	private boolean needCaptcha;
	
	public CaptchaInfoResult(){
	}
	
	public CaptchaInfoResult(boolean needCaptcha){
		this.needCaptcha=needCaptcha;
	}

	public boolean isNeedCaptcha() {
		return needCaptcha;
	}
	
	public void setNeedCaptcha(boolean needCaptcha) {
		this.needCaptcha = needCaptcha;
	}
}
