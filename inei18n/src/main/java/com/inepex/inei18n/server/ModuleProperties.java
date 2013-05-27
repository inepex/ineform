package com.inepex.inei18n.server;

import java.util.Properties;

import com.inepex.inei18n.server.util.SimpleOnDemandProperties;

public class ModuleProperties {
	
	public final String[] languages;
	public final String userCsvPath;
	public final String devCsvPath;
	public final String csvSeparator;
	public final String sourceFolder;
	public final String serverPackage;
	
	public ModuleProperties(ClassLoader classLoader, String moduleName) {
		SimpleOnDemandProperties odp = new SimpleOnDemandProperties(classLoader, moduleName + ".properties");
		Properties properties = odp.getPropertiesInstance();
		
		languages = properties.getProperty("languages").split(",");
		userCsvPath = properties.getProperty("userCsvPath");
		csvSeparator = properties.getProperty("csvSeparator");
		sourceFolder = properties.getProperty("sourceFolder");
		serverPackage = properties.getProperty("serverPackage");
		
		//property for localization util
		devCsvPath=properties.getProperty("devCsvPath");
	}
}
