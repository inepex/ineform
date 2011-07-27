package com.inepex.example.ContactManager.client.navigation;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;

@Singleton
public class AppPlaceHandler extends PlaceHandler{

	@Inject
	public AppPlaceHandler(PlaceHierarchyProvider placeHierarchyProvider,
			MasterPage masterPage,
			HistoryProvider historyProvider,
			EventBus eventBus,
			AuthManager authManager) {
		super(placeHierarchyProvider, masterPage, authManager, historyProvider, eventBus);
	}
	
	@Override
	protected boolean specificAdjustPlaceShouldReturn(InePlace place) {
		return false;
	}

}
