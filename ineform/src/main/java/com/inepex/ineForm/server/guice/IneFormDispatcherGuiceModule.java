package com.inepex.ineForm.server.guice;

import com.google.inject.AbstractModule;
import com.inepex.ineForm.shared.dispatch.ActionObjectFactory;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;

public class IneFormDispatcherGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ManipulationObjectFactory.class).to(ActionObjectFactory.class);
	}

}
