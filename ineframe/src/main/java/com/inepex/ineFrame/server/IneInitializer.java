package com.inepex.ineFrame.server;

import com.google.inject.Provider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public interface IneInitializer {
    void registerAdditionalI18nModules(
        I18nStore_Server serverI18n,
        Provider<CurrentLang> currentLangProvider);

    void registerAssists(DescriptorStore descStore);

    void setupDefaults();

}
