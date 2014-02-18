package com.inepex.inei18n.server;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;

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
