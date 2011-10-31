package com.inepex.ineFrame.server.auth;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.CaptchaInfoAction;
import com.inepex.ineFrame.shared.auth.CaptchaInfoResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public class CaptchaInfoHandler extends AbstractIneHandler<CaptchaInfoAction, CaptchaInfoResult>{

	@Inject
	Provider<SessionScopedCaptchaInfo> captchaInfoProvider;
	
	@Override
	protected CaptchaInfoResult doExecute(CaptchaInfoAction action, ExecutionContext context) throws AuthenticationException, DispatchException {
		
		SessionScopedCaptchaInfo info = captchaInfoProvider.get();
		synchronized (info) {
			return new CaptchaInfoResult(info.needCaptcha());
		}
	}
	
	@Override
	public Class<CaptchaInfoAction> getActionType() {
		return CaptchaInfoAction.class;
	}
}
