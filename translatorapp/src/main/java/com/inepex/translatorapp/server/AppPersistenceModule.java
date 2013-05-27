package com.inepex.translatorapp.server;

import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.inepex.ineFrame.server.di.jpa.InePersistFilter;

public class AppPersistenceModule extends ServletModule {

	@Override
	protected void configureServlets() {
		install(new JpaPersistModule("translatorapp"));
		
		filter("/*").through(InePersistFilter.class);

		//TODO uncomment these line if you use LoginBox!! And you may need rename this class from AppPersistenceModule! 
		//Map<String, String> params = new HashMap<String, String>();
		//params.put("width", "200");
		//params.put("height", "50");
		//serve("/SimpleCaptcha.jpg").with(CaptchaServlet.class, params);
	}
}