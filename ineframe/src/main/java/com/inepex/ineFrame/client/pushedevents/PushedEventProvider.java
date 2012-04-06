package com.inepex.ineFrame.client.pushedevents;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.BatchAction;
import net.customware.gwt.dispatch.shared.BatchAction.OnException;
import net.customware.gwt.dispatch.shared.BatchResult;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.ConnectionFailedHandler;
import com.inepex.ineFrame.client.async.FullscreenStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.PushedActionContext;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.async.SilentStatusIndicator;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.shared.IneFrameProperties;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;


public class PushedEventProvider {
		
	private Map<Action<?>, PushedActionContext<?>> requestedActions 
		= new LinkedHashMap<Action<?>, PushedActionContext<?>>();
	
	Timer eventQueryTimer = null;
	
	int maxRetryNum = IneFrameProperties.DEFAULT_MAX_EVENT_QUERY_RETRIES;
	int failureCount = 0;
	int refreshInterval = IneFrameProperties.DEFAULT_EVENT_REFRESH_INTERVAL;
	boolean stoppedOnFailures = false;
	boolean useFullscreenStatusIndicatorOnce = false;
	boolean requeryAfterQueryCompleted = false;
	boolean isStarted = false;

	final SilentStatusIndicator silentStatusIndicator = new SilentStatusIndicator();
	final IneDispatch dispatch;
	final ConnectionFailedHandler connectionFailedHandler;
	
	BatchSuccess currentSuccessCallback = null;
	
	@Inject
	public PushedEventProvider(IneDispatch dispatch, ConnectionFailedHandler connectionFailedHandler) {
		this.dispatch = dispatch;
		this.connectionFailedHandler = connectionFailedHandler;
		eventQueryTimer = new RefreshTimer();
	}
	
	public boolean isQueryRunning() {
		return currentSuccessCallback != null;
	}
	
	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public void setMaxRetryNum(int maxRetryNum) {
		this.maxRetryNum = maxRetryNum;
	}
	
	private void reset() {
		currentSuccessCallback = null;
		stoppedOnFailures = false;
		isStarted = false;
		failureCount = 0;
	}
	
	private class RefreshTimer extends Timer {
		@Override
		public void run() {
			queryEvents();
		}
	}

	void queryEvents() {
		if (stoppedOnFailures) {
			reset();
			return;
		}
		
		isStarted = true;
		
		if (requestedActions.size() == 0)
			return;
		
		Action<?>[] actions = new Action[requestedActions.size()];
		
		int i = 0;
		List<PushedActionContext<?>> contextsInOrderForRunningActions 
				= new ArrayList<PushedActionContext<?>>();
		
		for (Action<?> action : requestedActions.keySet()) {
			actions[i] = action;
			PushedActionContext<?> contextForAction = requestedActions.get(action);
			contextForAction.onBeforeCallAction(action);
			
			if(contextForAction.customStatusIndicator != null)
				contextForAction.customStatusIndicator.onAsyncRequestStarted("");
			
			contextsInOrderForRunningActions.add(contextForAction);
			i++;
		}
		
		BatchAction batchAction = new BatchAction(OnException.CONTINUE, actions);
		
		AsyncStatusIndicator statusIndicator = useFullscreenStatusIndicatorOnce ? 
							 new FullscreenStatusIndicator() : silentStatusIndicator;
		useFullscreenStatusIndicatorOnce = false;
		
		currentSuccessCallback = new BatchSuccess(contextsInOrderForRunningActions);
		dispatch.execute(batchAction, currentSuccessCallback, statusIndicator);
	}
	
	private class BatchSuccess extends SuccessCallback<BatchResult> {
		List<PushedActionContext<?>> contextsInOrderForRunningActions = null;
		boolean stopRequested = false;

		public BatchSuccess(List<PushedActionContext<?>> contextsInOrderForRunningActions) {
			super();
			this.contextsInOrderForRunningActions = contextsInOrderForRunningActions;
		}

		@Override
		public void onFailure(Throwable caught) {
			if (stopRequested) {
				reset();
				return;
			}
			
			if (failureOccuredShouldStop())
				return;
			
			super.onFailure(caught);
			
			contextsInOrderForRunningActions = null;
			eventQueryTimer.schedule(refreshInterval);
		}
		
		@Override
		public void onSuccess(BatchResult result) {
			if (stopRequested) {
				return;
			}

			int numOfExceptions = 0;
			
			for (int i = 0; i < result.size(); i++) {
				PushedActionContext<?> contextForAction = contextsInOrderForRunningActions.get(i);
				Throwable ex = result.getException(i);
				if (ex != null) {
					if (ex instanceof AuthenticationException) {
						connectionFailedHandler.shutdown();
						Window.Location.reload();
						return;
					}
					
					// TODO: better log
					System.out.println(ex);
					ex.printStackTrace();
					numOfExceptions++;
					contextForAction.onFailure(ex);
					if(contextForAction.customStatusIndicator != null)
						contextForAction.customStatusIndicator.onGeneralFailure(ex.getMessage());
				} else {
					contextForAction.onSuccess(result.getResult(i));
					if(contextForAction.customStatusIndicator != null)
						contextForAction.customStatusIndicator.onSuccess("");
				}
			}
			
			
			if (numOfExceptions > 0) {
				if (failureOccuredShouldStop())
					return;
			}
			else
				failureCount = 0;
			
			// indicate end of async request if this request is still alive
			if (currentSuccessCallback == this)
				currentSuccessCallback = null;

			if (requeryAfterQueryCompleted)
				eventQueryTimer.schedule(1);
			else
				eventQueryTimer.schedule(refreshInterval);
			
			requeryAfterQueryCompleted = false;

		}
	}
	
	private boolean failureOccuredShouldStop() {
		failureCount++;
		
		if (failureCount >= maxRetryNum) {
			dispatch.getDefaultStatusIndicator().onGeneralFailure(IneFrameI18n.generalError());
			stoppedOnFailures = true;
			return true;
		}
		
		return false;
	}
	
	public <R extends Result> void addAction(Action<R> action, PushedActionContext<R> context) {
		addAction(action, context, false, false);
	}
	public <R extends Result> void addAction(Action<R> action, PushedActionContext<R> context
											, boolean triggerImmediateQuery
											, boolean useFullScreenStatusIndicator) {
		requestedActions.put(action, context);

		if (!isStarted) {
			useFullscreenStatusIndicatorOnce = useFullScreenStatusIndicator;
			eventQueryTimer.schedule(1);
			return;
		}
		
		if (triggerImmediateQuery)
			triggerImmediateQuery(useFullScreenStatusIndicator);
	}
	
	public <R extends Result> void removeAction(Action<R> action) {
		if (action == null) {
			System.out.println("PushedEventProvider: remove action was called with null.");
			return;
		}
		
		requestedActions.remove(action);
		
		if (requestedActions.size() == 0) {
			stopQueryingClearActions();
		}
	}
	
	public void stopQueryingClearActions(){
		requestedActions.clear();
		
		if (isQueryRunning()) {
			currentSuccessCallback.stopRequested = true;
		} else {
			eventQueryTimer.cancel();
		}		
		reset();
	}
	
	public void triggerImmediateQuery(boolean useFullScreenStatusIndicator) {
		useFullscreenStatusIndicatorOnce = useFullScreenStatusIndicator;
		if (isQueryRunning()) {
			requeryAfterQueryCompleted = true;
		} else {
			eventQueryTimer.schedule(1);
		}
	}
}
