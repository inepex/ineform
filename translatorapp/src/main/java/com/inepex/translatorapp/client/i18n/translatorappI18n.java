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
	
	public String inactiveAccount = "Your account is currently inactive. Please ask the administrator for roles!";
	public String pageNotFound = "Page not found";

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Your account is currently inactive. Please ask the administrator for roles!
	* <u><i>Magyarul:</i></u> A fiókod jelenleg inaktív. Kérj az adminisztrátoroktól jogokat!
	*/
	public static String inactiveAccount() {
		return moduleProvider.get().inactiveAccount;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page not found
	* <u><i>Magyarul:</i></u> Az oldal nem található
	*/
	public static String pageNotFound() {
		return moduleProvider.get().pageNotFound;
	}
}
