package com.inepex.ineFrame.server;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.server.i18n.ServerIneFrameI18nProvider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;

public class LocalizationInitializer {

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
		serverI18n.registerModule(new IneFrameI18n(new ServerIneFrameI18nProvider(currentLangProvider)));
		ineFrameInitilaizer.registerAdditionalI18nModules(serverI18n, currentLangProvider);
		serverI18n.loadAllModulesDataFormCsv(false);
		serverI18n.initI18nModules();
	}
	
	
}
