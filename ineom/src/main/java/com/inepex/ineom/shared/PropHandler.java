package com.inepex.ineom.shared;


public interface PropHandler {
	
	public void setProp(HasProp hasProp, String group, String key, Boolean value);
	
	public void setProp(HasProp hasProp, String group, String key, Double value);
	
	public void setProp(HasProp hasProp, String group, String key, String value);
	
	public Boolean getBooleanProp(HasProp hasProp, String group, String key);
	
	public Double getNumberProp(HasProp hasProp, String group, String key);
	
	public String getStringProp(HasProp hasProp, String group, String key);
	
	public String getStringPropFromGroupJson(String key, String json);
	
}
