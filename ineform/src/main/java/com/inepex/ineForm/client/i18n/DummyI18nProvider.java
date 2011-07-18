package com.inepex.ineForm.client.i18n;

import com.inepex.inei18n.shared.I18nModuleProvider;

public class DummyI18nProvider implements I18nModuleProvider<IneFormI18n_old>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1787744540659894227L;

	private static IneFormI18n_old instance;
	
	public DummyI18nProvider() {
		instance = new IneFormI18n_old();
	}
	
	@Override
	public IneFormI18n_old get() {
		return instance;
	}

}
