package com.inepex.example.ContactManager.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPageView;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

@GinModules({AppGinModule.class})
public interface AppGinjector extends Ginjector {

	public DefaultIneFrameMasterPageView getMasterPageView();
	public DispatchAsync getDispatchAsync();
	public EventBus getEventBus();
	public DescriptorStore getDescriptorStore();
	public AuthManager getAuthManager();
	public PlaceHierarchyProvider gePlaceHierarchyProvider();
	public PlaceHandler gePlaceHandler();
	public HistoryProvider getHistoryProvider();
	
}
