package com.inepex.inei18n.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.inject.Singleton;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieResult;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nStoreBase;

@Singleton
public class I18nStore_Client extends I18nStoreBase {
	
	public final static String LANG_COOKIE_ID="I18nStore_Client_last_used_lang";
	private String currentLanguage = "";

	/**
	 *  loads last used lang from cookie, creates a GetI18nModulesAndSetCurrentLangFromCookieAction from registered modules 
	 */
	public GetI18nModulesAndSetCurrentLangFromCookieAction getModuleQueryAction(boolean loadLangFromCookie) {
		Collection<String> moduleNames = new ArrayList<String>();
		for (String string : this.modulesByName.keySet()) {
			moduleNames.add(string);
		}
		
		String lastLang=null;
		if(loadLangFromCookie) {
			lastLang = Cookies.getCookie(LANG_COOKIE_ID);
		}
		
		return new GetI18nModulesAndSetCurrentLangFromCookieAction(lastLang, moduleNames);
	}
	
	public void onModulesQueriedSuccess(GetI18nModulesAndSetCurrentLangFromCookieResult result) {
		currentLanguage = result.currentLang;
		
		for (I18nModule i18nModule : modulesByName.values()) {
			ClientI18nProvider<?>  clientProvider 
				= (ClientI18nProvider<?>) i18nModule.getI18nProvider();
			clientProvider.setCurrentModule(result.i18nModulesByName.get(i18nModule.getModuleName()));
		}
	}
	public String getCurrentLanguage() {
		return currentLanguage;
	}
}
