package com.inepex.ineom.shared;


public abstract class PropHandler {
	
	public abstract void setProp(HasProp hasProp, String group, String key, Boolean value);
	
	public abstract void setProp(HasProp hasProp, String group, String key, Double value);
	
	public abstract void setProp(HasProp hasProp, String group, String key, String value, boolean strictMatch);
	
	public abstract void setProp(HasProp hasProp, String group, String key, String value);
	
	public abstract Boolean getBooleanProp(HasProp hasProp, String group, String key);
	
	public abstract Double getNumberProp(HasProp hasProp, String group, String key);
	
	public abstract String getStringProp(HasProp hasProp, String group, String key);
	
	public abstract String getStringPropFromGroupJson(String key, String json);
	
	public static String getStrictMatchKey(String key){
		return "#" + key;
	}
}
