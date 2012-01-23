package com.inepex.ineFrame.server.auth;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

@Singleton
public class GetAuthStatusHandler extends AbstractIneHandler<GetAuthStatusAction, AuthStatusResultBase>{

	private final Provider<SessionScopedAuthStat> authStatProvider;
	
	@Inject
	protected GetAuthStatusHandler(Provider<SessionScopedAuthStat> authStat) {
		this.authStatProvider=authStat;
	}

	@Override
	public Class<GetAuthStatusAction> getActionType() {
		return GetAuthStatusAction.class;
	}

	@Override
	protected AuthStatusResultBase doExecute(GetAuthStatusAction action, ExecutionContext context) throws AuthenticationException,
			DispatchException {
		
		SessionScopedAuthStat authStat = authStatProvider.get();
		synchronized (authStat) {
			return authStat.getAuthStatusResultBase();
		}
	}
}
