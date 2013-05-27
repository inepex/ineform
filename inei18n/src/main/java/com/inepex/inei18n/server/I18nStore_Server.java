package com.inepex.inei18n.server;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nStoreBase;
import com.inepex.inei18n.shared.LocalizedString;

public class I18nStore_Server extends I18nStoreBase {
	
	private static final Logger _logger = LoggerFactory.getLogger(I18nStore_Server.class);
		
	private final HashMap<String, TreeMap<String, LocalizedString>> localizablesByKeyByModule = new HashMap<>();
	private final HashSet<String> allLangs = new HashSet<>();
	
	@Inject
	public I18nStore_Server() {
	}
	
	public void addLocalizable(String module, LocalizedString localizable) {
		checkModuleAdded(module);
		
		TreeMap<String, LocalizedString> localizablesByKey = localizablesByKeyByModule.get(module);
		
		if (localizablesByKey == null) {
			localizablesByKey =  new TreeMap<String, LocalizedString>();
			localizablesByKeyByModule.put(module, localizablesByKey);
		}
		
		if (localizablesByKey.containsKey(localizable.getKey())) {
			_logger.info("List of localizables already contains key: {} in module {}", localizable.getKey(), module);
			return;
		}
		
		localizablesByKey.put(localizable.getKey(), localizable);
	}
	
	public HashSet<String> getAllLangs() {
		return allLangs;
	}
	
	private void checkModuleAdded(String module) {
		if (!modulesByName.containsKey(module)) {
			throw new RuntimeException("I18nModule '" + module + "' is not registered in serverSideStore!");
		}	
	}
	
	public void addLocalizables(String module, Collection<LocalizedString> localizables) {
		for (LocalizedString localizable : localizables) {
			addLocalizable(module, localizable);
		}
	}
	
	public I18nModule getI18nModuleByNameForLang(String module, String lang) {
		ServerI18nProvider<?> serverI18nProv = (ServerI18nProvider<?>) 
											   modulesByName.get(module).getI18nProvider();
		
		return serverI18nProv.getI18nForLang(lang);
	}
	
	public I18nModule getI18nModuleByNameForCurrentLang(String module) {
		return modulesByName.get(module).getI18nProvider().get();
	}
	
	public Map<String, LocalizedString> getLocalizablesForModule(String moduleName) {
		checkModuleAdded(moduleName);
		
		return localizablesByKeyByModule.get(moduleName);
	}
	
	public void loadAllModulesDataFormCsv(boolean isDevMode) {
		
		for (I18nModule i18nModule : modulesByName.values()) {
			I18nModuleConverter moduleConv = new I18nModuleConverter(i18nModule.getClass());
			if (isDevMode)
				moduleConv.loadDataFromDefaultCsvDev();
			else
				moduleConv.loadDataFromDefaultCsvRuntime();
			
			allLangs.addAll(Arrays.asList(moduleConv.getModuleLanguages()));
			
			addLocalizables(i18nModule.getModuleName(), moduleConv.getLocalizables());
		}
	}
	
	public void initI18nModules() {
		for (I18nModule i18nModule : modulesByName.values()) {
			if(i18nModule.getI18nProvider()==null || !(i18nModule.getI18nProvider() instanceof ServerI18nProvider))
					continue;
			
			ServerI18nProvider<?> serverI18nProv = (ServerI18nProvider<?>)i18nModule.getI18nProvider();
			
			TreeMap<String, LocalizedString> localizablesByKey = localizablesByKeyByModule.get(i18nModule.getModuleName());
			
			for (LocalizedString localizable : localizablesByKey.values()) {
				for(String langKey : localizable.getLocalizedMap().keySet()) {
					serverI18nProv.setByKeyAndLang(langKey
												 , localizable.getKey()
												 , localizable.getLocalizedMap().get(langKey));
				}
			} 
		}
	}
	
}
