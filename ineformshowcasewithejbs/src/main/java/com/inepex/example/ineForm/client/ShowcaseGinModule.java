package com.inepex.example.ineForm.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.inepex.ineForm.client.form.widgets.customkvo.ActionBasedOdFinder;
import com.inepex.ineForm.client.form.widgets.customkvo.OdFinder;
import com.inepex.ineForm.shared.dispatch.ActionBasedObjectFinder;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;

public class ShowcaseGinModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(AuthManager.class).to(NoAuthManager.class).in(Singleton.class);
		bind(OdFinder.class).to(ActionBasedOdFinder.class);
		bind(ObjectFinder.class).to(ActionBasedObjectFinder.class);
	}
}
