package com.inepex.ineom.shared;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface PropHandler {
	
	public void setProp(AssistedObject ao, String group, String key, Boolean value);
	
	public void setProp(AssistedObject ao, String group, String key, Double value);
	
	public void setProp(AssistedObject ao, String group, String key, String value);
	
	public Boolean getBooleanProp(AssistedObject ao, String group, String key);
	
	public Double getNumberProp(AssistedObject ao, String group, String key);
	
	public String getStringProp(AssistedObject ao, String group, String key);
	
	public String getStringPropFromGroupJson(String key, String json);
	
}
