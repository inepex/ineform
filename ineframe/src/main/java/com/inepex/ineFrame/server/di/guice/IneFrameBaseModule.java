package com.inepex.ineFrame.server.di.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.util.CETDateProviderSrv;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ServerCurrentLang;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class IneFrameBaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(I18nStore_Server.class).in(Singleton.class);
		bind(CurrentLang.class).to(ServerCurrentLang.class).in(Singleton.class);
		bind(DescriptorStore.class).to(MultiLangDescStore.class).in(Singleton.class);
		bind(DateProvider.class).to(CETDateProviderSrv.class).in(Singleton.class);
	}

}
