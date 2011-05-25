package com.inepex.ineFrame.test;

import com.google.inject.Provider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class DefaultIneFrameClientSideTestBase extends IneFrameClientSideTestBase {

	@Override
	public void registerAdditionalI18nModules(I18nStore_Server serverI18n, Provider<CurrentLang> currentLangProvider) { 
	}

	@Override
	public void registerAssists(DescriptorStore descStore) {
	}

	@Override
	public void setupDefaults() {
	}

}
