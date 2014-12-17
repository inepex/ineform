package com.inepex.inei18n.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class I18nModule implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;

	private String moduleName;
	
	public Map<String, String> textMap = new HashMap<String, String>();
	
	public I18nModule() {
	}
	
	public I18nModule(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}
	
	public static String upperFirst(String s){
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public Map<String, String> getTextMap() {
		return textMap;
	}
	
	public abstract I18nModuleProvider<?> getI18nProvider();
	
}
