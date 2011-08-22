package com.inepex.example.ineForm.server;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.inepex.core.module.IneModule;

public class TestServletModule extends ServletModule{

	@Override
	protected void configureServlets() {
		serve("/TestServlet").with(TestServlet.class);
		bind(IneModule.class).to(ShowcaseModule.class).in(Singleton.class);
	}
}
