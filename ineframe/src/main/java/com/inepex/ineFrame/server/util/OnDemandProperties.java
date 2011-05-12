package com.inepex.ineFrame.server.util;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.ineFrame._context.Context;

public class OnDemandProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(OnDemandProperties.class);

	private final String properyFileName;
	private Properties properties;

	public OnDemandProperties(String properyFileName) {
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
		if (!"".equals(Context.current) || !"default".equals(Context.current)) {
			String contextSpecificPropfile = properyFileName + "." + Context.current;
			try {
				
				InputStream s = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(contextSpecificPropfile);
				properties.load(s);
				s.close();
			} catch (Exception e) {
				logger.warn("Could not read context specific configuration file: "
						+ contextSpecificPropfile);
			}
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
