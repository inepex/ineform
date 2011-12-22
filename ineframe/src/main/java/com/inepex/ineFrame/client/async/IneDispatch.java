package com.inepex.ineFrame.client.async;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class IneDispatch extends IneDispatchBase<Action<? extends Result>> {

	private DispatchAsync dispatcher;
	
	
	@Inject
	public IneDispatch(DispatchAsync dispatcher, AsyncStatusIndicator defaultStatusIndicator,
			EventBus eventBus, ConnectionFailedHandler connectionFailedHandler) {
		super(connectionFailedHandler, eventBus);
		this.dispatcher = dispatcher;
		this.defaultStatusIndicator = defaultStatusIndicator;
		this.eventBus = eventBus;
	}
	
	public DispatchAsync getDispatcher() {
		return dispatcher;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doExecute(
			Action action,
			IneAsyncCallback callback) {
		dispatcher.execute(action, callback);
		
	}
	
}
