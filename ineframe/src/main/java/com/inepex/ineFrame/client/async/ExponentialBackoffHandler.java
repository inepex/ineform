package com.inepex.ineFrame.client.async;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.inepex.ineFrame.shared.GetDescStore;
import com.inepex.ineFrame.shared.GetDescStoreResult;

public class ExponentialBackoffHandler implements ConnectionFailedHandler {

	private class WaitTimer extends Timer {

		@Override
		public void run() {
			doConnectionCheck();
		}
		
	}
	
	private AsyncCallback<GetDescStoreResult> callback = new AsyncCallback<GetDescStoreResult>() {

		@Override
		public void onFailure(Throwable caught) {
			onFail();
		}

		@Override
		public void onSuccess(GetDescStoreResult result) {
			ExponentialBackoffHandler.this.onSuccess();
		}
	};
	
	private final DispatchAsync dispatcher;
	private int failCount = 0;	
	private int[] delays = {1000*5, 1000*10, 1000*60, 1000*60*5};
	
	private Timer timer = new WaitTimer();
	
	private EventBus eventBus;
	
	private boolean recoverInProgress = false;
	
	private boolean shuttedDown = false;
	
	@Inject
	public ExponentialBackoffHandler(EventBus eventBus,
			DispatchAsync dispatcher) {
		this.eventBus = eventBus;
		this.dispatcher = dispatcher;
	}

	@Override
	public boolean startRecover() {
		if (!shuttedDown && !recoverInProgress){
			recoverInProgress = true;
			failCount = 0;
			onFail();
		}
		
		return true;
	}
	
	
	private void onFail() {
		timer.cancel();
		if (!shuttedDown) {
			int delay = delays[delays.length-1];
			if (failCount < delays.length) delay = delays[failCount];
			eventBus.fireEvent(new ConnectionEvent(true, delay));
			failCount++;
			timer.schedule(delay);
		}
	}

	private void onSuccess() {
		timer.cancel();
		recoverInProgress = false;
		if (failCount > 0) {
			failCount = 0;
			eventBus.fireEvent(new ConnectionEvent(false));
		}		
	}

	@Override
	public boolean isOnline() {
		return !recoverInProgress;
	}
	
	private void doConnectionCheck(){
		dispatcher.execute(new GetDescStore(), callback);
	}

	@Override
	public void shutdown() {
		timer.cancel();
		shuttedDown = true;
	}

}
