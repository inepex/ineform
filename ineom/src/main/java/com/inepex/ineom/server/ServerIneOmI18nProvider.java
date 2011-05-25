package com.inepex.ineom.server;

import com.google.inject.Provider;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.i18n.IneOmI18n;

public class ServerIneOmI18nProvider extends ServerI18nProvider<IneOmI18n> {

	private static final long serialVersionUID = 1L;

	public ServerIneOmI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<IneOmI18n> getModuleClass() {
		return IneOmI18n.class;
	}

	@Override
	protected IneOmI18n getVirgineI18nModule() {
		return new IneOmI18n(this);
	}
}
