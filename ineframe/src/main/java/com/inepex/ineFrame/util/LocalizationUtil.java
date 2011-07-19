package com.inepex.ineFrame.util;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.ineFrame.client.i18n.IneFrameI18n_old;
import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.LocalizedString;

public class LocalizationUtil {

	final static Logger logger = LoggerFactory
			.getLogger(LocalizationUtil.class);
	
	public static void main(String[] args) {
		generateIneFrameSourceFromCsv();
	}

	private static void generateIneFrameSourceFromCsv() {
		I18nModuleConverter converter = new I18nModuleConverter(IneFrameI18n_old.class);
		converter.loadDataFromDefaultCsvDev();
		Map<String, LocalizedString> localizables = converter.getLocalizablesMap();
		
		try {
			
			I18nStore_Server serverStore = new I18nStore_Server();
			serverStore.registerModule(new IneFrameI18n_old(null));
			
			serverStore.addLocalizables(IneFrameI18n_old.MODULE_NAME, localizables.values());
			I18nModuleConverter srvI18nConverter = new I18nModuleConverter(IneFrameI18n_old.class
					, serverStore.getLocalizablesForModule(IneFrameI18n_old.MODULE_NAME));
			srvI18nConverter.saveCsvToDefaultPath();
			srvI18nConverter.generateModuleFile();
			srvI18nConverter.generateServerModuleProviderFile();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}