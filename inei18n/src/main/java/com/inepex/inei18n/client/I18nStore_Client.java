package com.inepex.inei18n.client;

import java.util.ArrayList;
import java.util.Collection;

import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.GetI18nModulesAction;
import com.inepex.inei18n.shared.GetI18nModulesResult;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nStoreBase;

public class I18nStore_Client extends I18nStoreBase {
	
	public static String currentLanguage = "";
	
	public GetI18nModulesAction getModuleQueryAction() {
		Collection<String> moduleNames = new ArrayList<String>();
		for (String string : this.modulesByName.keySet()) {
			moduleNames.add(string);
		}
		
		GetI18nModulesAction action = new GetI18nModulesAction(moduleNames);
		
		return action;
	}
	
	public void onModulesQueriedSuccess(GetI18nModulesResult result) {
		currentLanguage = result.currentLang;
		
		for (I18nModule i18nModule : modulesByName.values()) {
			ClientI18nProvider<?>  clientProvider 
				= (ClientI18nProvider<?>) i18nModule.getI18nProvider();
			clientProvider.setCurrentModule(result.i18nModulesByName.get(i18nModule.getModuleName()));
		}
	}
}
