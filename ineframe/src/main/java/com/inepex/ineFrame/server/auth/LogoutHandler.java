package com.inepex.ineFrame.server.auth;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.LogoutAction;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.dispatch.GenericResult;

@Singleton
public class LogoutHandler extends AbstractIneHandler<LogoutAction, GenericResult> {

	private final Provider<SessionScopedAuthStat> authStatProvider;
	
	@Inject
	LogoutHandler(Provider<SessionScopedAuthStat> authStat) {
		this.authStatProvider=authStat;
	}

	@Override
	public Class<LogoutAction> getActionType() {
		return LogoutAction.class;
	}

	@Override
	protected GenericResult doExecute(LogoutAction action, ExecutionContext context) throws AuthenticationException,
			DispatchException {
		
		SessionScopedAuthStat authStat = authStatProvider.get();
		synchronized (authStat) {
			authStat.clearState();
		}
		
		return new GenericResult("", true);
	}
	
}
