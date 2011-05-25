package com.inepex.ineFrame.server;

import com.google.inject.Provider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public interface IneFrameInitializer {
	void registerAdditionalI18nModules(I18nStore_Server serverI18n, Provider<CurrentLang> currentLangProvider);
	void registerAssists(DescriptorStore descStore);
	void setupDefaults();

}
