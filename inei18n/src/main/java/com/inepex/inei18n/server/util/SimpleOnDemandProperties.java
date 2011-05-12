package com.inepex.inei18n.server.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleOnDemandProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SimpleOnDemandProperties.class);

	private final String properyFileName;
	private Properties properties;

	public SimpleOnDemandProperties(String properyFileName) {
		super();
		this.properyFileName = properyFileName;
	}
	
	private void loadProperties(){
		try {
			InputStream s = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(properyFileName);
			properties = new Properties();
			properties.load(s);
			s.close();
		} catch (Exception e) {
			logger.error("Could not read configuration file: "
					+ properyFileName, e);
			return;
		}
	}
	
	public String getProperty(String key) {
		return getPropertiesInstance().getProperty(key);
	}
	
	public Properties getPropertiesInstance() {
		if(properties == null)
			loadProperties();
		return properties;
	}
	
	
}
