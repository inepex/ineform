package com.inepex.translatorapp.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootPanel;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.IneFrameEntryPoint;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.shared.util.date.DateHelper;
import com.inepex.inei18n.client.I18nStore_Client;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.translatorapp.client.gin.AppGinjector;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.client.navigation.AppPlaceHierarchyProvider;

public class App extends IneFrameEntryPoint {

	public static AppGinjector INJECTOR = GWT.create(AppGinjector.class);
	
	static {
		Cookies.setCookie(I18nStore_Client.LANG_COOKIE_ID, "en", new Date(System.currentTimeMillis()+DateHelper.dayInMs*50));
	}
	
	public App() {
		super(INJECTOR.getDispatchAsync(), INJECTOR.getEventBus(), INJECTOR.getAuthManager(), INJECTOR.getDescriptorStore(),
				INJECTOR.getHistoryProvider(), INJECTOR.getI18nStore_Client());
	}
	
	@Override
	public void onIneModuleLoad() {		
		NavigationProperties.defaultPlace=AppPlaceHierarchyProvider.LOGGEDIN;
		NavigationProperties.noRightPlace=AppPlaceHierarchyProvider.LOGIN;
		NavigationProperties.wrongTokenPlace=AppPlaceHierarchyProvider.LOGGEDIN + Node.ID_SEPARATOR + AppPlaceHierarchyProvider.PAGENOTFOUND;
		NavigationProperties.loginPlace = AppPlaceHierarchyProvider.LOGIN;
		NavigationProperties.defaultWindowTitle=translatorappI18n.appname();
		
		registerDescriptors();
		
		RootPanel.get().add(INJECTOR.getMasterPage().getView());
		INJECTOR.gePlaceHandler().fireInitialPlace();
	}

	private void registerDescriptors() {
	}
	
	@Override
	protected void registerAdditionalI18nModules() {
		clientI18nStore.registerModule(new IneOmI18n(new ClientI18nProvider<IneOmI18n>()));
		clientI18nStore.registerModule(new IneFormI18n(new ClientI18nProvider<IneFormI18n>()));
		clientI18nStore.registerModule(new translatorappI18n(new ClientI18nProvider<translatorappI18n>()));
	}
}
