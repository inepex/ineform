package com.inepex.ineom.shared;

import java.util.Map;



public abstract class PropHandler {
	
	public abstract void setProp(HasProp hasProp, String group, String key, Boolean value);
	
	public abstract void setProp(HasProp hasProp, String group, String key, Double value);
	
	public abstract void setProp(HasProp hasProp, String group, String key, String value, boolean strictMatch);
	
	public abstract void setProp(HasProp hasProp, String group, String key, String value);
	
	public abstract Boolean getBooleanProp(HasProp hasProp, String group, String key);
	
	public abstract Double getNumberProp(HasProp hasProp, String group, String key);
	
	public abstract String getStringProp(HasProp hasProp, String group, String key);
	
	public abstract String getStringPropFromGroupJson(String key, String json);
	
	public abstract Boolean getBooleanPropFromGroupJson(String key, String json);
	
	public abstract Double getNumberPropFromGroupJson(String key, String json);
	
	public abstract Map<String, Object> getPropMap(HasProp hasProp, String id);
	
	public abstract void setProp(HasProp o, String group, String key, Object value);
	
	public static String getStrictMatchKey(String key){
		return "#" + key;
	}
	
	public void mergeProps(HasProp from, HasProp to) {
		for(String id : from.getAllPropsJson().keySet()){
			String toPropsJson = to.getPropsJson(id);
			if(toPropsJson == null){
				to.getAllPropsJson().put(id, from.getPropsJson(id));
			}else{
				Map<String, Object> propMap = getPropMap(from, id);
				for(String key : propMap.keySet()){
					setProp(to, id, key, propMap.get(key));
				}
			}
		}
	}

}
