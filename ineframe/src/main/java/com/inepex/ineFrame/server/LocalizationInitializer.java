package com.inepex.ineFrame.server;

import com.google.inject.Provider;
import com.inepex.inei18n.client.IneFormI18n_old;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ServerIneFormI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;

public class LocalizationInitializer {

	private final I18nStore_Server serverI18n;
	private final IneFrameInitializer ineFrameInitilaizer;
	private final Provider<CurrentLang> currentLangProvider;
	
	public LocalizationInitializer(I18nStore_Server serverI18n, IneFrameInitializer ineFrameInitilaizer,
			Provider<CurrentLang> currentLangProvider) {
		this.serverI18n = serverI18n;
		this.ineFrameInitilaizer = ineFrameInitilaizer;
		this.currentLangProvider = currentLangProvider;
	}

	public void doInitialize() {
		serverI18n.registerModule(new IneFormI18n_old(new ServerIneFormI18nProvider(currentLangProvider)));
		ineFrameInitilaizer.registerAdditionalI18nModules(serverI18n);
		serverI18n.loadAllModulesDataFormCsv(false);
		serverI18n.initI18nModules();
	}
	
	
}
