package com.inepex.example.ContactManager.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.google.inject.Singleton;
import com.inepex.example.ContactManager.server.handler.LoginHandler;
import com.inepex.ineForm.server.customkvo.ObjectDescHandler;
import com.inepex.ineFrame.server.auth.CaptchaInfoHandler;
import com.inepex.ineFrame.server.auth.GetAuthStatusHandler;
import com.inepex.ineFrame.server.auth.LogoutHandler;
import com.inepex.ineFrame.server.di.jpa.PersistInitializer;
import com.inepex.ineFrame.server.util.CETDateProviderSrv;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.ObjectDescAction;
import com.inepex.ineFrame.shared.auth.CaptchaInfoAction;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.auth.LogoutAction;


public class AppGuiceModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bind(DateProvider.class).to(CETDateProviderSrv.class).in(Singleton.class);
		
		bind(PersistInitializer.class).asEagerSingleton();
		
		//authentication
		bindHandler(GetAuthStatusAction.class, GetAuthStatusHandler.class);
		bindHandler(LogoutAction.class, LogoutHandler.class);
		bindHandler(LoginAction.class, LoginHandler.class);
		bindHandler(CaptchaInfoAction.class, CaptchaInfoHandler.class);
		
		bindHandler(ObjectDescAction.class, ObjectDescHandler.class);
	}

}