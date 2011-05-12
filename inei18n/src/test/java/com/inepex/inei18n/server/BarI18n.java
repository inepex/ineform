package com.inepex.inei18n.server;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class BarI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "BarI18n";
	
	static I18nModuleProvider<BarI18n> moduleProvider;
	
	public BarI18n() {
		super(MODULE_NAME);
	}
		
	public BarI18n(I18nModuleProvider<BarI18n> moduleProvider) {
		super(MODULE_NAME);
		BarI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public String bar1 = "bar1_EN";
	public String bar2 = "bar2_EN";

	
	/**
	* <u><i>Description:</i></u> Description for bar1 <br />
	* <u><i>In English:</i></u> bar1_EN
	* <u><i>Magyarul:</i></u> bar1_HU
	*/
	public static String bar1() {
		return moduleProvider.get().bar1;
	}
	
	/**
	* <u><i>Description:</i></u> Description for bar2 <br />
	* <u><i>In English:</i></u> bar2_EN
	* <u><i>Magyarul:</i></u> bar2_HU
	*/
	public static String bar2() {
		return moduleProvider.get().bar2;
	}
}