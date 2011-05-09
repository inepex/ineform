package com.inepex.ineFrame.server.di.guice;

import javax.servlet.http.HttpServlet;

import com.google.inject.servlet.ServletModule;

public class IneFrameBaseServletModule extends ServletModule {
	final String applicationName;
	final Class<? extends HttpServlet> servletClass;
	public IneFrameBaseServletModule(String applicationName, Class<? extends HttpServlet> servletClass) {
		this.applicationName = applicationName;
		this.servletClass = servletClass;
	}
	
	@Override
	protected void configureServlets() {
		serve("/" + applicationName + "/dispatch").with(servletClass);
	}
	
}
