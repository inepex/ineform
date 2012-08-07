package com.inepex.ineForm.test;

import java.util.List;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class DefaultIneFormClientSideTestBase extends IneFormClientSideTestbase {


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
