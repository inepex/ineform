package com.inepex.example.ContactManager.server.i18n;
import com.inepex.example.ContactManager.client.i18n.CMI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerCMI18nProvider extends ServerI18nProvider<CMI18n> {

	private static final long serialVersionUID = 1L;

	public ServerCMI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<CMI18n> getModuleClass() {
		return CMI18n.class;
	}

	@Override
	protected CMI18n getVirgineI18nModule() {
		return new CMI18n(this);
	}
}
