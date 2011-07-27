package com.inepex.example.ContactManager.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.inepex.example.ContactManager.server.handler.LoginHandler;
import com.inepex.ineFrame.server.auth.GetAuthStatusHandler;
import com.inepex.ineFrame.server.auth.LogoutHandler;
import com.inepex.ineFrame.server.di.jpa.PersistInitializer;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.auth.LogoutAction;

public class AppGuiceModule  extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bind(PersistInitializer.class).asEagerSingleton();
		
		//authentication
		bindHandler(GetAuthStatusAction.class, GetAuthStatusHandler.class);
		bindHandler(LogoutAction.class, LogoutHandler.class);
		bindHandler(LoginAction.class, LoginHandler.class);
	}

}