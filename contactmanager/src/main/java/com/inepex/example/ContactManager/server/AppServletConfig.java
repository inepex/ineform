package com.inepex.example.ContactManager.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.inepex.ineForm.server.guice.IneFormActionHanlderModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseActionHanlderModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseServletModule;

public class AppServletConfig  extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppPersistenceModule()
									, new IneFrameBaseServletModule("ContactManager", AppDispatchServlet.class)
									, new IneFrameBaseActionHanlderModule()
									, new IneFrameBaseModule()
									, new IneFormActionHanlderModule()
									, new AppGuiceModule());
	}
	
}
