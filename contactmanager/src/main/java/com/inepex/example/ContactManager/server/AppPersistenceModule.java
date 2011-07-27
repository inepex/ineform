package com.inepex.example.ContactManager.server;

import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.inepex.ineFrame.server.di.jpa.InePersistFilter;

public class AppPersistenceModule extends ServletModule {

	@Override
	protected void configureServlets() {
		install(new JpaPersistModule("ContactManager"));
		
		filter("/*").through(InePersistFilter.class);

	}
}