package com.inepex.example.ContactManager.server;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.inepex.ineFrame.server.CaptchaServlet;
import com.inepex.ineFrame.server.di.jpa.InePersistFilter;

public class AppServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		install(new JpaPersistModule("ContactManager"));
		
		filter("/*").through(InePersistFilter.class);

		Map<String, String> params = new HashMap<String, String>();
		params.put("width", "200");
		params.put("height", "50");
		serve("/SimpleCaptcha.jpg").with(CaptchaServlet.class, params);
	}
}