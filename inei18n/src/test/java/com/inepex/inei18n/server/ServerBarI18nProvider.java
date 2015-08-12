package com.inepex.inei18n.server;

import com.inepex.inei18n.server.BarI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerBarI18nProvider extends ServerI18nProvider<BarI18n> {

    private static final long serialVersionUID = 1L;

    public ServerBarI18nProvider(Provider<CurrentLang> currentLangProvider) {
        super(currentLangProvider);
    }

    @Override
    protected Class<BarI18n> getModuleClass() {
        return BarI18n.class;
    }

    @Override
    public BarI18n getVirgineI18nModule() {
        BarI18n module = new BarI18n(this);
        initTexts(module);
        return module;
    }

    public void initTexts(BarI18n module) {
        module.getTextMap().put("bar1", "bar1_EN");
        module.getTextMap().put("bar2", "bar2_EN");
    }
}
