package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.shared.EventHandler;

public interface PlaceChangedHandler extends EventHandler {
	
	public void onPlaceChange(PlaceChangedEvent e);
	
}