package com.inepex.ineFrame.test;

import java.util.List;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class DefaultIneFrameClientSideTestBase extends IneFrameClientSideTestBase {

	@Override
	public void registerAssists(DescriptorStore descStore) {
	}

	@Override
	public void setupDefaults() {
	}

	@Override
	public List<Class<? extends I18nModule>> listUsedI18nClasses() {
		return null;
	}

}
