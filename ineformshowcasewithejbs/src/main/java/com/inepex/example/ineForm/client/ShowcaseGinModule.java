package com.inepex.example.ineForm.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;

public class ShowcaseGinModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(AuthManager.class).to(NoAuthManager.class).in(Singleton.class);
	}
}
