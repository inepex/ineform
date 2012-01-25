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
	private final AbstractLoginHandler<? extends AuthUser, AuthStatusResultBase> loginHandler;
	
	@Inject
	protected GetAuthStatusHandler(Provider<SessionScopedAuthStat> authStat, AbstractLoginHandler<AuthUser, AuthStatusResultBase> loginHandler) {
		this.authStatProvider=authStat;
		this.loginHandler=loginHandler;
	}

	@Override
	public Class<GetAuthStatusAction> getActionType() {
		return GetAuthStatusAction.class;
	}

	@Override
	protected AuthStatusResultBase doExecute(GetAuthStatusAction action, ExecutionContext context) throws AuthenticationException,
			DispatchException {
		
		SessionScopedAuthStat authStat = authStatProvider.get();
		AuthStatusResultBase result;
		
		synchronized (authStat) {
			result = authStat.getAuthStatusResultBase();
		}
		
		// if the user is already logged in, we don't need to do anything more
		if(result!=null && result.getUserId()!=null && result.getDisplayName()!=null && result.getRoles()!=null)
			return result;
		
		String userEmail = action.getUserEmail();
		String userUUID = action.getUserUUID();
		if(result== null && userEmail!=null && userUUID!=null){
			result=loginHandler.createResultBase();
			loginHandler.checkSignedInUUIDForUser(userEmail, userUUID, result);
		}
	
		return result;
	}
}
