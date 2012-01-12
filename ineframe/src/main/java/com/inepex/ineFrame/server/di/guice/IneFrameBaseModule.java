package com.inepex.ineFrame.server.di.guice;

import net.customware.gwt.dispatch.server.ActionHandler;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.di.jpa.PersistInitializer;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ServerCurrentLang;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class IneFrameBaseModule extends AbstractModule {
	private Class<? extends ActionHandler<LoginAction, AuthStatusResultBase>> loginHandler;
	private boolean jpa = true;
	
	public IneFrameBaseModule(Class<? extends ActionHandler<LoginAction, AuthStatusResultBase>> loginHandler,
			boolean jpa) {
		this.jpa = jpa;
		this.loginHandler = loginHandler;
	}
	
	@Override
	protected void configure() {
		install(new IneFrameBaseActionHanlderModule(loginHandler));
		bind(I18nStore_Server.class).in(Singleton.class);
		bind(CurrentLang.class).to(ServerCurrentLang.class).in(Singleton.class);
		bind(DescriptorStore.class).to(MultiLangDescStore.class).in(Singleton.class);
		if (jpa) bind(PersistInitializer.class).asEagerSingleton();
	}

}
