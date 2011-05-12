package com.inepex.ineForm.test;

import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class DefaultIneFormClientSideTestBase extends IneFormClientSideTestbase {

	@Override
	public void registerAdditionalI18nModules(I18nStore_Server serverI18n) {
	}

	@Override
	public void registerAssists(DescriptorStore descStore) {
	}

	@Override
	public void setupDefaults() {
	}

}
