package com.inepex.inei18n.shared;

import java.io.Serializable;

public abstract class I18nModule implements Serializable {

	private static final long serialVersionUID = 1L;

	String moduleName;
	
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
	
	public abstract I18nModuleProvider<?> getI18nProvider();
	
}
