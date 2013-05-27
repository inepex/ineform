package com.inepex.translatorapp.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class translatorappI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "translatorappI18n";
	
	static I18nModuleProvider<translatorappI18n> moduleProvider;
	
	public translatorappI18n() {
		super(MODULE_NAME);
	}
		
	public translatorappI18n(I18nModuleProvider<translatorappI18n> moduleProvider) {
		super(MODULE_NAME);
		translatorappI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public String dummy = "dummy text in english";
	
	/**
	* <u><i>Description:</i></u> description of this string <br />
	* <u><i>In English:</i></u> dummy text in english
	* <u><i>Magyarul:</i></u> and it's hungarian translate: dummy szöveg angolul
	*/
	public static String dummy() {
		return moduleProvider.get().dummy;
	}
}
