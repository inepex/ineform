package com.inepex.ineFrame.client.auth;

import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public class DefaultAuthManager extends AbstractAuthManager<AuthStatusResultBase> {

	@Inject
	public DefaultAuthManager(IneDispatch dispatcher) {
		super(dispatcher);
	}

}
