package com.inepex.ineFrame.client.async;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class IneDispatch extends IneDispatchBase {

	private DispatchAsync dispatcher;

	@Inject
	public IneDispatch(DispatchAsync dispatcher, AsyncStatusIndicator defaultStatusIndicator, EventBus eventBus) {
		this.dispatcher = dispatcher;
		this.defaultStatusIndicator = defaultStatusIndicator;
		this.eventBus = eventBus;
	}
	
	public DispatchAsync getDispatcher() {
		return dispatcher;
	}

	public <A extends Action<R>, R extends Result> void execute(A action
			, SuccessCallback<R> callback) {	
		dispatcher.execute(action, new IneAsyncCallback<R>(callback));	
		defaultStatusIndicator.onAsyncRequestStarted("");
	}

	public <A extends Action<R>, R extends Result> void execute(A action
			, SuccessCallback<R> callback 
			, AsyncStatusIndicator statusIndicator) {		
		statusIndicator = statusIndicator == null ? defaultStatusIndicator : statusIndicator;
		dispatcher.execute(action, new IneAsyncCallback<R>(callback, statusIndicator));
		statusIndicator.onAsyncRequestStarted("");
	}
	
}
