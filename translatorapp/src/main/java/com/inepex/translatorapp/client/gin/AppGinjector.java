package com.inepex.translatorapp.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@GinModules({ AppGinModule.class })
public interface AppGinjector extends Ginjector {

    public MasterPage getMasterPage();

    public DispatchAsync getDispatchAsync();

    public EventBus getEventBus();

    public DescriptorStore getDescriptorStore();

    public AuthManager getAuthManager();

    public PlaceHierarchyProvider gePlaceHierarchyProvider();

    public PlaceHandler gePlaceHandler();

    public HistoryProvider getHistoryProvider();

    public I18nStore_Client getI18nStore_Client();
}
