package com.inepex.example.ContactManager;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.LocalizedString;

public class LocalizationUtil {

	final static Logger logger = LoggerFactory
			.getLogger(LocalizationUtil.class);
	
	public static void main(String[] args) {
		generateIneFormSourceFromCsv();
	}

	private static void generateIneFormSourceFromCsv() {
		I18nModuleConverter converter = new I18nModuleConverter(CMI18n.class);
		converter.loadDataFromDefaultCsvDev();
		Map<String, LocalizedString> localizables = converter.getLocalizablesMap();
		
		try {
			
			I18nStore_Server serverStore = new I18nStore_Server();
			serverStore.registerModule(new CMI18n(null));
			
			serverStore.addLocalizables(CMI18n.MODULE_NAME, localizables.values());
			I18nModuleConverter srvI18nConverter = new I18nModuleConverter(CMI18n.class
					, serverStore.getLocalizablesForModule(CMI18n.MODULE_NAME));
			srvI18nConverter.saveCsvToDefaultPath();
			srvI18nConverter.generateModuleFile();
			srvI18nConverter.generateServerModuleProviderFile();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
