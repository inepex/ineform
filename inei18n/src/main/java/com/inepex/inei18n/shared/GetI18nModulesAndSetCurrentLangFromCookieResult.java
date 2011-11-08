package com.inepex.inei18n.shared;

import java.io.Serializable;
import java.util.Map;

public class GetI18nModulesAndSetCurrentLangFromCookieResult extends SimpleResult implements Serializable{

	private static final long serialVersionUID = 1L;

	public String currentLang; 
	public Map<String, I18nModule> i18nModulesByName;

	public GetI18nModulesAndSetCurrentLangFromCookieResult() {
	}

	public GetI18nModulesAndSetCurrentLangFromCookieResult(String currentLang,
			Map<String, I18nModule> i18nModulesByName) {
		this.currentLang = currentLang;
		this.i18nModulesByName = i18nModulesByName;
	}

}
