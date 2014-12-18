package com.inepex.ineFrame.test;

import com.google.inject.Provider;
import com.inepex.ineFrame.server.IneInitializer;
import com.inepex.ineFrame.server.LocalizationInitializer;
import com.inepex.ineFrame.server.MockI18n;
import com.inepex.ineFrame.server.i18n.ServerIneFrameI18nProvider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.I18nModule;

public class TestLocalizationInitializer extends LocalizationInitializer{

	public TestLocalizationInitializer(I18nStore_Server serverI18n,
			IneInitializer ineFrameInitilaizer,
			Provider<CurrentLang> currentLangProvider) {
		super(serverI18n, ineFrameInitilaizer, currentLangProvider);
	}

	@Override
	protected I18nModule getIneFrameModule(Provider<CurrentLang> currentLangProvider) {
		return MockI18n.mock(ServerIneFrameI18nProvider.class);
	}

}
