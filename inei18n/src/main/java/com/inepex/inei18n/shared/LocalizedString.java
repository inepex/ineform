package com.inepex.inei18n.shared;

import java.util.HashMap;
import java.util.Map;

public class LocalizedString  {

	private String key;
	private String description;
	private Map<String, String> localizedMap = new HashMap<String, String>();
	
	public LocalizedString(){
	}
	
	public LocalizedString(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getLocalizedMap() {
		return localizedMap;
	}

	public void setLocalizedMap(Map<String, String> localizedMap) {
		this.localizedMap = localizedMap;
	}  

	public String getString(String lang) {
		return localizedMap.get(lang);
	}
	
	public void putString(String lang, String str) {
		localizedMap.put(lang, str);
	}
	
	
}
