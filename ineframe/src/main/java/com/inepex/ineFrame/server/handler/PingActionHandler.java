package com.inepex.ineFrame.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.shared.PingAction;
import com.inepex.ineFrame.shared.PingResult;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;

public class PingActionHandler extends AbstractIneHandler<PingAction, PingResult>{

	private Provider<SessionScopedAuthStat> authStatProvider;

	@Inject
	public PingActionHandler(Provider<SessionScopedAuthStat> authStatProvider) {
		this.authStatProvider = authStatProvider;
	}
	
	@Override
	public Class<PingAction> getActionType() {
		return PingAction.class;
	}

	@Override
	protected PingResult doExecute(PingAction action, ExecutionContext context) throws AuthenticationException, DispatchException {
		SessionScopedAuthStat authStat = authStatProvider.get();
		PingResult pingResult = new PingResult();
		synchronized (authStat) {
			AuthStatusResultBase  result = authStat.getAuthStatusResultBase();
			pingResult.setSessionAlive(result.getUserId() != null);
		}
		return pingResult;
	}

}
