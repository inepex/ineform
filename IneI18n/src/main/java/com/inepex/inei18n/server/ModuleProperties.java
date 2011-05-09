package com.inepex.inei18n.server;

import java.util.Properties;

import com.inepex.inei18n.server.util.SimpleOnDemandProperties;

public class ModuleProperties {
	
	Properties properties = new Properties();
	final String moduleName;
	
	public String[] languages;
	public String userCsvPath;
	public String csvSeparator;
	public String sourceFolder;
	public String serverPackage;
	
	public ModuleProperties(String moduleName) {
		this.moduleName = moduleName;
		SimpleOnDemandProperties odp = new SimpleOnDemandProperties(moduleName + ".properties");
		properties = odp.getPropertiesInstance();
		
		loadProperties();
	}
	
	private void loadProperties() {
		languages = properties.getProperty("languages").split(",");
		userCsvPath = properties.getProperty("userCsvPath");
		csvSeparator = properties.getProperty("csvSeparator");
		sourceFolder = properties.getProperty("sourceFolder");
		serverPackage = properties.getProperty("serverPackage");
	}
}
