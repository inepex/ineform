package com.inepex.inei18n.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ModuleProperties;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.LocalizedString;

public class LocalizationUtilLogic {

	private final static Logger logger = LoggerFactory.getLogger(LocalizationUtilLogic.class);
	
	private final Class<? extends I18nModule> i18nClass;
	private final String moduleName;
	
	public LocalizationUtilLogic(Class<? extends I18nModule> i18nClass, String name) {
		this.i18nClass=i18nClass;
		this.moduleName=name;
	}

	public void generateIneFormSourceFromCsv() {
		Map<String, LocalizedString> localizables;
		String downloadUrl = getDownloadUrl();
		if(downloadUrl!=null)
			localizables=downloadLocalizables(downloadUrl);
		else
			localizables=loadLocalizablesFormCsv();
		
		try {
			
			I18nStore_Server serverStore = new I18nStore_Server();
			serverStore.registerModule(i18nClass.newInstance());
			
			serverStore.addLocalizables(moduleName, localizables.values());
			I18nModuleConverter srvI18nConverter = new I18nModuleConverter(i18nClass
					, serverStore.getLocalizablesForModule(moduleName));
			srvI18nConverter.saveCsvToDefaultPath();
			srvI18nConverter.generateModuleFile();
			srvI18nConverter.generateServerModuleProviderFile();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private String getDownloadUrl() {
		return new ModuleProperties(i18nClass.getClassLoader(), moduleName).downloadUrl;
	}

	private Map<String, LocalizedString> downloadLocalizables(String downloadUrl) {
		logger.info("Downloading rows from: {}", downloadUrl);
		try {
			URL url = new URL(downloadUrl);
			InputStream r = url.openStream();
			String json = CharStreams.toString(new InputStreamReader(r));
			
			ObjectMapper objectMapper = new ObjectMapper();
			DownloadLocalizablesDto dto = objectMapper.readValue(json, DownloadLocalizablesDto.class);
			
			if(dto.getWarning()!=null) {
				for(String str : dto.getWarning()) 
					logger.warn(str);
			}
			
			if(dto.getLocalizables()==null || dto.getLocalizables().isEmpty()) {
				throw new RuntimeException("No localizables in the downloaded object.");
			}
			
			return new TreeMap<>(dto.getLocalizables());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, LocalizedString> loadLocalizablesFormCsv() {
		I18nModuleConverter converter = new I18nModuleConverter(i18nClass);
		converter.loadDataFromDefaultCsvDev();
		return converter.getLocalizablesMap();
	}

	public void generateHelperForEnums(List<Class<Enum<?>>> enums) {
		if(enums==null || enums.size()==0)
			return;
		
		List<EnumI18nExtractor.EnumClassWithPostfix> enumList = new ArrayList<EnumI18nExtractor.EnumClassWithPostfix>(enums.size());
		for(Class<Enum<?>> e : enums)
			enumList.add(new EnumI18nExtractor.EnumClassWithPostfix(e));

		EnumI18nExtractor.generateI18nAccessHelpersForEnums(Thread
				.currentThread().getContextClassLoader(),
				moduleName, enumList);
	}
}
