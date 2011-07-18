package com.inepex.ineForm.server.i18n;

import com.google.inject.Provider;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;

public class ServerIneFormI18nProvider extends ServerI18nProvider<IneFormI18n> {

	private static final long serialVersionUID = 1L;

	public ServerIneFormI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<IneFormI18n> getModuleClass() {
		return IneFormI18n.class;
	}

	@Override
	protected IneFormI18n getVirgineI18nModule() {
		return new IneFormI18n(this);
	}
}
