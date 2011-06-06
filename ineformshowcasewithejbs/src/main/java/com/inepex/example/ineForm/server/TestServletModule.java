package com.inepex.example.ineForm.server;

import com.google.inject.servlet.ServletModule;

public class TestServletModule extends ServletModule{

	@Override
	protected void configureServlets() {
		serve("/TestServlet").with(TestServlet.class);
	}
}
