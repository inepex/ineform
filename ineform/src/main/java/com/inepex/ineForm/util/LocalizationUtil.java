package com.inepex.ineForm.util;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.LocalizedString;

public class LocalizationUtil {

	final static Logger logger = LoggerFactory
			.getLogger(LocalizationUtil.class);
	
	public static void main(String[] args) {
		generateIneFormSourceFromCsv();
//		generateHelperForEnums();
	}
	
//	private static void generateHelperForEnums() {
//		List<EnumClassWithPostfix> enumList = new ArrayList<EnumI18nExtractor.EnumClassWithPostfix>();
//		enumList.add(new EnumClassWithPostfix(ODFieldType.class));
//
//		EnumI18nExtractor.generateI18nAccessHelpersForEnums(Thread
//				.currentThread().getContextClassLoader(),
//				IneFormI18n.MODULE_NAME, enumList);
//	}

	private static void generateIneFormSourceFromCsv() {
		I18nModuleConverter converter = new I18nModuleConverter(IneFormI18n.class);
		converter.loadDataFromDefaultCsvDev();
		Map<String, LocalizedString> localizables = converter.getLocalizablesMap();
		
		try {
			
			I18nStore_Server serverStore = new I18nStore_Server();
			serverStore.registerModule(new IneFormI18n(null));
			
			serverStore.addLocalizables(IneFormI18n.MODULE_NAME, localizables.values());
			I18nModuleConverter srvI18nConverter = new I18nModuleConverter(IneFormI18n.class
					, serverStore.getLocalizablesForModule(IneFormI18n.MODULE_NAME));
			srvI18nConverter.saveCsvToDefaultPath();
			srvI18nConverter.generateModuleFile();
			srvI18nConverter.generateServerModuleProviderFile();
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
