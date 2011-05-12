package com.inepex.inei18n.server;
import com.inepex.inei18n.server.FooI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerFooI18nProvider extends ServerI18nProvider<FooI18n> {

	private static final long serialVersionUID = 1L;

	public ServerFooI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<FooI18n> getModuleClass() {
		return FooI18n.class;
	}

	@Override
	protected FooI18n getVirgineI18nModule() {
		return new FooI18n(this);
	}
}