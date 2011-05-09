package com.inepex.inei18n.util;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.inei18n.client.IneFormI18n_old;
import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.LocalizedString;

public class LocalizationUtil {

	final static Logger logger = LoggerFactory
			.getLogger(LocalizationUtil.class);

	private static Map<String, LocalizedString> localizables;
	
	public static void main(String[] args) {
		generateSourceFromCsv();
	}

	private static void generateSourceFromCsv() {
		I18nModuleConverter converter = new I18nModuleConverter(IneFormI18n_old.class);
		converter.loadDataFromDefaultCsvDev();
		localizables = converter.getLocalizablesMap();
		try {
			createFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createFiles() throws IOException {
		I18nStore_Server serverStore = new I18nStore_Server();
		serverStore.registerModule(new IneFormI18n_old(null));
		
		serverStore.addLocalizables(IneFormI18n_old.MODULE_NAME, localizables.values());
		I18nModuleConverter srvI18nConverter = new I18nModuleConverter(IneFormI18n_old.class
				, serverStore.getLocalizablesForModule(IneFormI18n_old.MODULE_NAME));
		srvI18nConverter.saveCsvToDefaultPath();
		srvI18nConverter.generateModuleFile();
		srvI18nConverter.generateServerModuleProviderFile();
	}
}
