package com.inepex.ineom;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.LocalizedString;
import com.inepex.ineom.shared.i18n.IneOmI18n;

public class LocalizationUtil {

	final static Logger logger = LoggerFactory
			.getLogger(LocalizationUtil.class);
	
	public static void main(String[] args) {
		generateIneFormSourceFromCsv();
	}

	private static void generateIneFormSourceFromCsv() {
		I18nModuleConverter converter = new I18nModuleConverter(IneOmI18n.class);
		converter.loadDataFromDefaultCsvDev();
		Map<String, LocalizedString> localizables = converter.getLocalizablesMap();
		
		try {
			
			I18nStore_Server serverStore = new I18nStore_Server();
			serverStore.registerModule(new IneOmI18n(null));
			
			serverStore.addLocalizables(IneOmI18n.MODULE_NAME, localizables.values());
			I18nModuleConverter srvI18nConverter = new I18nModuleConverter(IneOmI18n.class
					, serverStore.getLocalizablesForModule(IneOmI18n.MODULE_NAME));
			srvI18nConverter.saveCsvToDefaultPath();
			srvI18nConverter.generateModuleFile();
			srvI18nConverter.generateServerModuleProviderFile();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
