package com.inepex.ineFrame.server.di.guice;

import net.customware.gwt.dispatch.server.ActionHandler;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.auth.GetAuthStatusHandler;
import com.inepex.ineFrame.server.di.jpa.PersistInitializer;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ServerCurrentLang;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class IneFrameBaseModule extends AbstractModule {
	private Class<? extends ActionHandler<LoginAction, AuthStatusResultBase>> loginHandler;
	private Class<? extends ActionHandler<GetAuthStatusAction, AuthStatusResultBase>> getAuthStatusHandler = GetAuthStatusHandler.class;
	private boolean jpa = true;
	
	public IneFrameBaseModule(Class<? extends ActionHandler<LoginAction, AuthStatusResultBase>> loginHandler,
			boolean jpa) {
		this.jpa = jpa;
		this.loginHandler = loginHandler;
	}

	public IneFrameBaseModule setGetAuthStatusHandler(Class<? extends ActionHandler<GetAuthStatusAction, AuthStatusResultBase>> getAuthStatusHandler){
		this.getAuthStatusHandler = getAuthStatusHandler;
		return this;
	}
	
	@Override
	protected void configure() {
		install(new IneFrameBaseActionHandlerModule(loginHandler).setGetAuthStatusHandler(getAuthStatusHandler));
		bind(I18nStore_Server.class).in(Singleton.class);
		bind(CurrentLang.class).to(ServerCurrentLang.class).in(Singleton.class);
		bind(DescriptorStore.class).to(MultiLangDescStore.class).in(Singleton.class);
		if (jpa) bind(PersistInitializer.class).asEagerSingleton();
	}

}
