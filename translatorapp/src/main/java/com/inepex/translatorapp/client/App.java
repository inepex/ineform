package com.inepex.translatorapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.IneFrameEntryPoint;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.translatorapp.client.gin.AppGinjector;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public class App extends IneFrameEntryPoint {

	public static AppGinjector INJECTOR = GWT.create(AppGinjector.class);
	
	public App() {
		super(INJECTOR.getDispatchAsync(), INJECTOR.getEventBus(), INJECTOR.getAuthManager(), INJECTOR.getDescriptorStore(),
				INJECTOR.getHistoryProvider(), INJECTOR.getI18nStore_Client());
	}
	
	@Override
	public void onIneModuleLoad() {		
		RootPanel.get().add(INJECTOR.getMasterPage().getView());
		INJECTOR.gePlaceHandler().fireInitialPlace();
	}

	@Override
	protected void registerAdditionalI18nModules() {
		clientI18nStore.registerModule(new IneOmI18n(new ClientI18nProvider<IneOmI18n>()));
		clientI18nStore.registerModule(new IneFormI18n(new ClientI18nProvider<IneFormI18n>()));
		clientI18nStore.registerModule(new translatorappI18n(new ClientI18nProvider<translatorappI18n>()));
	}
}
