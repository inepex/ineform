package com.inepex.ineFrame.server.di.guice;

import net.customware.gwt.dispatch.server.ActionHandler;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.auth.AbstractLoginHandler;
import com.inepex.ineFrame.server.auth.AuthUser;
import com.inepex.ineFrame.server.auth.DefaultLoginHandler;
import com.inepex.ineFrame.server.auth.GetAuthStatusHandler;
import com.inepex.ineFrame.server.di.jpa.PersistInitializer;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.inei18n.server.ApplicationLangs;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.WebServerCurrentLang;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.ConcurrentDescStoreMapCreator;
import com.inepex.ineom.server.DescStoreCreator;
import com.inepex.ineom.server.JavaJsonDifference;
import com.inepex.ineom.server.JavaPropHandler;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.JsonDifference;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.descriptorstore.ClientDescStoreCreator;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStoreMapCreator;

public class IneFrameBaseModule extends AbstractModule {

	private Class<? extends AbstractLoginHandler<AuthUser, AuthStatusResultBase>> loginHandler = DefaultLoginHandler.class;
	private Class<? extends ActionHandler<GetAuthStatusAction, AuthStatusResultBase>> getAuthStatusHandler = GetAuthStatusHandler.class;
	private Class<? extends DescStoreCreator> descStoreCreatorClass = ClientDescStoreCreator.class;
	private Class<? extends ApplicationLangs> appLangs = DefaultApplicationLangs.class;
	
	private boolean jpa = true;
	
	public IneFrameBaseModule(boolean jpa) {
		this.jpa = jpa;
	}

	public IneFrameBaseModule setGetAuthStatusHandler(Class<? extends ActionHandler<GetAuthStatusAction, AuthStatusResultBase>> getAuthStatusHandler){
		this.getAuthStatusHandler = getAuthStatusHandler;
		return this;
	}
	
	public IneFrameBaseModule setLoginHandler(Class<? extends AbstractLoginHandler<AuthUser, AuthStatusResultBase>> loginHandler){
		this.loginHandler = loginHandler;
		return this;
	}
	
	public IneFrameBaseModule setAppLangs(Class<? extends ApplicationLangs> appLangs) {
		this.appLangs = appLangs;
		return this;
	}
	
	@Override
	protected void configure() {
		install(new IneFrameBaseActionHandlerModule().setLoginHandler(loginHandler).setGetAuthStatusHandler(getAuthStatusHandler));
		bind(I18nStore_Server.class).in(Singleton.class);
		bind(CurrentLang.class).to(WebServerCurrentLang.class).in(Singleton.class);
		bind(ApplicationLangs.class).to(appLangs).in(Singleton.class);
		bind(DescriptorStore.class).to(MultiLangDescStore.class).in(Singleton.class);
		bind(DescriptorStoreMapCreator.class).to(ConcurrentDescStoreMapCreator.class).in(Singleton.class);
		bind(DescStoreCreator.class).to(descStoreCreatorClass).in(Singleton.class);
		if (jpa) bind(PersistInitializer.class).asEagerSingleton();
		bind(JsonDifference.class).to(JavaJsonDifference.class);
		bind(PropHandler.class).to(JavaPropHandler.class);
	}
	
	public IneFrameBaseModule setDescStoreInnerStructure(Class<? extends DescStoreCreator> descStoreCreatorClass) {
		this.descStoreCreatorClass=descStoreCreatorClass;
		return this;
	}

}
