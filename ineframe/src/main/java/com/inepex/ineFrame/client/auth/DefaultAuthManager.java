package com.inepex.ineFrame.client.auth;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.IneDispatch;

public class DefaultAuthManager extends AbstractAuthManager {

	@Inject
	public DefaultAuthManager(IneDispatch dispatcher, EventBus eventBus) {
		super(dispatcher, eventBus);
	}

}
