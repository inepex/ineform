package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AnalyticsHandler implements PlaceChangedHandler {

	private boolean accountSet = false;
	
	@Inject
	public AnalyticsHandler(EventBus eventBus) {
		eventBus.addHandler(PlaceChangedEvent.TYPE, this);
	}

	@Override
	public void onPlaceChange(PlaceChangedEvent e) {
		if (!accountSet) {
			return;
		}
		trackPageview(e.getTokenWithoutParams());
	}
	
	public void setAccount(String accountId){
		accountSet = true;
		setAnalyticsAccount(accountId);
	}
	
	
	private native void setAnalyticsAccount(String accountId) /*-{
	$wnd._gaq.push([ '_setAccount', accountId ]);
	}-*/;
	
	public native void trackPageview(String page) /*-{
	$wnd._gaq.push([ '_trackPageview', page ]);
	}-*/;
}
