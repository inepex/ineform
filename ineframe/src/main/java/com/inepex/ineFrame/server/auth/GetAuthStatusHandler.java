package com.inepex.ineFrame.server.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class GetAuthStatusHandler extends
		AbstractIneHandler<GetAuthStatusAction, AuthStatusResultBase> {
	
	private static final Logger _logger = LoggerFactory
			.getLogger(GetAuthStatusHandler.class);

	private final Provider<SessionScopedAuthStat> authStatProvider;
	private final AbstractLoginHandler<? extends AuthUser, AuthStatusResultBase> loginHandler;

	@Inject
	protected GetAuthStatusHandler(Provider<SessionScopedAuthStat> authStat,
			AbstractLoginHandler<AuthUser, AuthStatusResultBase> loginHandler) {
		this.authStatProvider = authStat;
		this.loginHandler = loginHandler;
	}

	@Override
	public Class<GetAuthStatusAction> getActionType() {
		return GetAuthStatusAction.class;
	}

	@Override
	protected AuthStatusResultBase doExecute(GetAuthStatusAction action,
			ExecutionContext context) throws AuthenticationException,
			DispatchException {

		SessionScopedAuthStat authStat = authStatProvider.get();

		synchronized (authStat) {
			AuthStatusResultBase  result = authStat.getAuthStatusResultBase();

			String userEmail = action.getUserEmail();
			String userUUID = action.getUserUUID();
			
			// user is already logged in or no staysignedin logic
			if (result != null || userEmail==null || userUUID==null)
				return result;

			//updating auth stat
			result = loginHandler.createResultBase();
			loginHandler.checkSignedInUUIDForUserAndLogUserIntoIfCorrect(userEmail, userUUID, result);
			
			_logger.debug("Auth stat updated to: {}", authStatProvider.get());
			return result; 
		}
	}
}
