package com.inepex.ineFrame.client.async;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineFrame.shared.exceptions.AuthorizationException;
import com.inepex.ineFrame.shared.exceptions.PageNotFoundException;

public class IneDispatchBase {

	protected AsyncStatusIndicator defaultStatusIndicator;
	protected EventBus eventBus;
	
	protected boolean swallowStatusCodeException = false;
	
	public void startSwallowStatuscodeException() {
		swallowStatusCodeException = true;
	}
	
	public void setStatusIndicator(AsyncStatusIndicator statusIndicator){
		defaultStatusIndicator = statusIndicator;
	}

	public AsyncStatusIndicator getDefaultStatusIndicator() {
		return defaultStatusIndicator;
	}

	protected class IneAsyncCallback<R> implements AsyncCallback<R > {
		
		private final SuccessCallback<R> successCallback;
		private final  AsyncStatusIndicator statusIndicator;
		
		public IneAsyncCallback(SuccessCallback<R> successCallback) {
			this.successCallback = successCallback;
			this.statusIndicator = defaultStatusIndicator;
		}
		
		public IneAsyncCallback(SuccessCallback<R> successCallback,
				AsyncStatusIndicator statusIndicator) {
			this.successCallback = successCallback;
			this.statusIndicator = statusIndicator;
		}

		@Override
		public void onFailure(Throwable caught) {
			if (caught instanceof AuthorizationException){
				System.out.println("TODO: authorization exception in IneDispatch"); //maybe a page not found message
			} else if (caught instanceof AuthenticationException) {
				startSwallowStatuscodeException();
				Window.Location.reload();
				return;
			} else if (caught instanceof PageNotFoundException){
				PlaceRequestEvent pre = new PlaceRequestEvent();
				pre.setHierarchicalTokensWithParam(NavigationProperties.pageNotFoundPlace);
				eventBus.fireEvent(pre);
				successCallback.onFailure(caught);
				statusIndicator.onSuccess("");
				return;
			} else if( caught instanceof StatusCodeException) {
				//if server is down refresh page, to get a browser 404 error page
				if(((StatusCodeException) caught).getStatusCode()==0) {
					Window.Location.reload();
					return;
				}
			}
			
			if (swallowStatusCodeException && caught instanceof StatusCodeException) {
				return;
			}
			
			successCallback.onFailure(caught);
			statusIndicator.onGeneralFailure(caught.getMessage());
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(R result) {
			statusIndicator.onSuccess("");
			successCallback.onSuccess(result);
		}
	}
	
	public static abstract class SuccessCallback<R> {
		public void onFailure(Throwable caught){
			
		}
		public abstract void onSuccess(R result);
	}
	
	public static abstract class PushedActionContext<R extends Result> extends SuccessCallback<Result> {
		public AsyncStatusIndicator customStatusIndicator = null;		
		
		public PushedActionContext() {
		}
		
		public PushedActionContext(AsyncStatusIndicator customStatusIndicator) {
			this.customStatusIndicator = customStatusIndicator;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(Result result) {
			onCastedSuccess((R) result);
		}
		
		public abstract void onCastedSuccess(R castedResult);
		
		/**
		 * Override this to get notified about when the action is invoked
		 * @param action
		 */
		public void onBeforeCallAction(Action<?> action){
			
		}
	}
	
}
