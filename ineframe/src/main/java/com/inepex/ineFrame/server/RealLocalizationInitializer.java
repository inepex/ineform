package com.inepex.ineFrame.server;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.server.i18n.ServerIneFrameI18nProvider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.I18nModule;

public class RealLocalizationInitializer extends LocalizationInitializer {

    public RealLocalizationInitializer(
        I18nStore_Server serverI18n,
        IneInitializer ineFrameInitilaizer,
        Provider<CurrentLang> currentLangProvider) {
        super(serverI18n, ineFrameInitilaizer, currentLangProvider);
    }

    @Override
    protected I18nModule getIneFrameModule(Provider<CurrentLang> currentLangProvider) {
        return new IneFrameI18n(new ServerIneFrameI18nProvider(currentLangProvider));
    }

}
