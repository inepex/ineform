package com.inepex.ineFrame.test;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.server.IneInitializer;
import com.inepex.ineFrame.server.LocalizationInitializer;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.ineom.TestI18nModuleProvider;

public class TestLocalizationInitializer extends LocalizationInitializer{

	public TestLocalizationInitializer(I18nStore_Server serverI18n,
			IneInitializer ineFrameInitilaizer,
			Provider<CurrentLang> currentLangProvider) {
		super(serverI18n, ineFrameInitilaizer, currentLangProvider);
	}

	@Override
	protected I18nModule getIneFrameModule(Provider<CurrentLang> currentLangProvider) {
		return TestI18nModuleProvider.createTestI18nProvider(IneFrameI18n.class);
	}

}