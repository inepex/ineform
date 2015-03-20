package com.inepex.translatorapp.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.inepex.ineForm.client.gin.IneFormDispatcherGinModule;
import com.inepex.ineForm.client.gin.IneFormGinModule;
import com.inepex.ineFrame.client.async.ExponentialBackoffHandler;
import com.inepex.ineFrame.client.gin.IneFrameGinModule;
import com.inepex.translatorapp.client.TranslatorAppFormWidgetFactoryManager;
import com.inepex.translatorapp.client.navigation.AppPlaceHandler;
import com.inepex.translatorapp.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.translatorapp.client.page.popup.ChangeModuleLangPopup;
import com.inepex.translatorapp.client.page.popup.ChangeModuleLangPopup.ChangeModuleLangPopupFactory;

public class AppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new IneFormGinModule()
			.setConnectionFailedHandler(ExponentialBackoffHandler.class)
			.setFormWidgetFactory(TranslatorAppFormWidgetFactoryManager.class));
		install(new IneFormDispatcherGinModule());
		install(new IneFrameGinModule(AppPlaceHierarchyProvider.class, AppPlaceHandler.class));
		
		install(new GinFactoryModuleBuilder()
	     .implement(ChangeModuleLangPopup.class, ChangeModuleLangPopup.class)
	     .build(ChangeModuleLangPopupFactory.class));
	}

}