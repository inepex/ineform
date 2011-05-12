package com.inepex.inei18n.server;

import com.google.inject.Provider;
import com.inepex.inei18n.client.IneFormI18n_old;
import com.inepex.inei18n.shared.CurrentLang;

public class ServerIneFormI18nProvider extends ServerI18nProvider<IneFormI18n_old> {

	private static final long serialVersionUID = 1L;

	public ServerIneFormI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<IneFormI18n_old> getModuleClass() {
		return IneFormI18n_old.class;
	}

	@Override
	protected IneFormI18n_old getVirgineI18nModule() {
		return new IneFormI18n_old(this);
	}
}
