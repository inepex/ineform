package com.inepex.translatorapp.server.i18n;
import com.google.inject.Provider;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public class ServertranslatorappI18nProvider extends ServerI18nProvider<translatorappI18n> {

	private static final long serialVersionUID = 1L;

	public ServertranslatorappI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<translatorappI18n> getModuleClass() {
		return translatorappI18n.class;
	}

	@Override
	protected translatorappI18n getVirgineI18nModule() {
		return new translatorappI18n(this);
	}
}
