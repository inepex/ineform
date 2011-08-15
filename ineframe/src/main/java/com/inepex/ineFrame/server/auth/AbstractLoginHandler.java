package com.inepex.ineFrame.server.auth;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Provider;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public abstract class AbstractLoginHandler<U extends AuthUser, R extends AuthStatusResultBase> extends AbstractIneHandler<LoginAction, AuthStatusResultBase>{

	private final Provider<SessionScopedAuthStat> authStatProvider;
	
	protected AbstractLoginHandler(Provider<SessionScopedAuthStat> authStat) {
		this.authStatProvider=authStat;
	}

	@Override
	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}

	@Override
	protected AuthStatusResultBase doExecute(LoginAction action, ExecutionContext context) throws AuthenticationException,
			DispatchException {
		if(action.getUserName()==null || action.getPassword()==null)
			return null;
		
		U user = findByUserNameAndPassword(action.getUserName(), action.getPassword());
		
		if(user==null)
			return null;
		
		R result = createResultBase();
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
}
