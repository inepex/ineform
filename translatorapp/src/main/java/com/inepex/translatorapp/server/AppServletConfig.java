package com.inepex.translatorapp.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class AppServletConfig  extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppPersistenceModule()
									, new AppGuiceModule());
	}
	
}
