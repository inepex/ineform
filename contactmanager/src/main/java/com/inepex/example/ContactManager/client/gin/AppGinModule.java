package com.inepex.example.ContactManager.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHandler;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineForm.client.gin.IneFormDispatcherGinModule;
import com.inepex.ineForm.client.gin.IneFormGinModule;
import com.inepex.ineFrame.client.async.ExponentialBackoffHandler;
import com.inepex.ineFrame.client.gin.IneFrameGinModule;

public class AppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		install(new IneFormGinModule().setConnectionFailedHandler(ExponentialBackoffHandler.class));
		install(new IneFormDispatcherGinModule());
		install(new IneFrameGinModule(AppPlaceHierarchyProvider.class, AppPlaceHandler.class));
	}

}
