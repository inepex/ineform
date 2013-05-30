package com.inepex.translatorapp.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.inepex.ineForm.server.customkvo.CustomObjectDescHandler;
import com.inepex.ineForm.server.guice.IneFormActionHanlderModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseServletModule;
import com.inepex.ineFrame.shared.CustomObjectDescAction;
import com.inepex.translatorapp.server.handler.LoginHandler;
import com.inepex.translatorapp.server.handler.RegActionHandler;
import com.inepex.translatorapp.server.handler.TransTableListActionHandler;
import com.inepex.translatorapp.shared.action.RegAction;
import com.inepex.translatorapp.shared.action.TransTableListAction;

public class AppGuiceModule  extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		install(new IneFrameBaseServletModule("translatorapp", AppDispatchServlet.class));
		install(new IneFrameBaseModule(true).setLoginHandler(LoginHandler.class));
		install(new IneFormActionHanlderModule());
		
		bindHandler(CustomObjectDescAction.class, CustomObjectDescHandler.class);
		bindHandler(RegAction.class, RegActionHandler.class);
		bindHandler(TransTableListAction.class, TransTableListActionHandler.class);
	}

}