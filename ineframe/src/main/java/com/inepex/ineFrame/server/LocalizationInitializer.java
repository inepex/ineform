package com.inepex.ineFrame.server;

import com.google.inject.Provider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.I18nModule;

public abstract class LocalizationInitializer {

	private final I18nStore_Server serverI18n;
	private final IneInitializer ineFrameInitilaizer;
	private final Provider<CurrentLang> currentLangProvider;
	
	public LocalizationInitializer(I18nStore_Server serverI18n, IneInitializer ineFrameInitilaizer,
			Provider<CurrentLang> currentLangProvider) {
		this.serverI18n = serverI18n;
		this.ineFrameInitilaizer = ineFrameInitilaizer;
		this.currentLangProvider = currentLangProvider;
	}

	public void doInitialize() {
		serverI18n.registerModule(getIneFrameModule(currentLangProvider));
		ineFrameInitilaizer.registerAdditionalI18nModules(serverI18n, currentLangProvider);
		serverI18n.loadAllModulesDataFormCsv(false);
		serverI18n.initI18nModules();
	}

	protected abstract I18nModule getIneFrameModule(Provider<CurrentLang> currentLangProvider);
}
