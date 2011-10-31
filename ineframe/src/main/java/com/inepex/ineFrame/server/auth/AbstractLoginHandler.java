package com.inepex.ineFrame.server.auth;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import nl.captcha.Captcha;

import com.google.inject.Provider;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public abstract class AbstractLoginHandler<U extends AuthUser, R extends AuthStatusResultBase> extends AbstractIneHandler<LoginAction, AuthStatusResultBase>{

	private final Provider<SessionScopedAuthStat> authStatProvider;
	private final Provider<SessionScopedCaptchaInfo> captchaInfoProvider;
	private final Provider<HttpSession> sesionProvider;
	
	protected AbstractLoginHandler(Provider<SessionScopedAuthStat> authStat,
			Provider<HttpSession> sesionProvider, Provider<SessionScopedCaptchaInfo> captchaInfoProvider) {
		this.authStatProvider = authStat;
		this.captchaInfoProvider=captchaInfoProvider;
		this.sesionProvider=sesionProvider;
	}

	@Override
	protected AuthStatusResultBase doExecute(LoginAction action, ExecutionContext context) throws AuthenticationException,
			DispatchException {
		U user;
		
		SessionScopedCaptchaInfo captchaInfo = captchaInfoProvider.get();
		synchronized (captchaInfo) {
			if(action.getUserName()==null || action.getPassword()==null) {
				captchaInfo.registerIncorrectAnswer();
				return new AuthStatusResultBase(captchaInfo.needCaptcha());
			}
				
			
			if(captchaInfo.needCaptcha()) {
				//incorrect request
				if(action.getCaptchaAnswer()==null || 
						!action.getCaptchaAnswer().equals(((Captcha)sesionProvider.get().getAttribute(Captcha.NAME)).getAnswer())) {
					captchaInfo.registerIncorrectAnswer();
					return new AuthStatusResultBase(captchaInfo.needCaptcha());
				}
			}
		
			user = findByUserNameAndPassword(action.getUserName(), action.getPassword());
			if(user==null) {
				//incorrect password
				captchaInfo.registerIncorrectAnswer();
				return new AuthStatusResultBase(captchaInfo.needCaptcha());
			} else {
				captchaInfo.registerCorrectAnswer();
			}
		}
		
		R result = createResultBase();
		result.setSuccess(true);
		result.setDisplayName(user.getDisplayName());
		result.setRoles(user.getAllowedRoles());
		result.setUserId(user.getUserId());
		
		mapAdditional(user, result);
		
		SessionScopedAuthStat authStat = authStatProvider.get();
		synchronized (authStat) {
			authStat.clearState();
			authStat.setUserId(user.getUserId());
			authStat.setAuthStatusResultBase(result);
		}
		
		return result;
	}
	
	protected abstract void mapAdditional(U user, R result);

	/**
	 * 
	 * @return - the selected authUser or null if password or userName is incorrect
	 */
	protected abstract U findByUserNameAndPassword(String userAuthString, String password);

	/**
	 * @return an empty result base object
	 */
	protected abstract R createResultBase();
	
	@Override
	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}
}
