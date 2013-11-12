package com.inepex.ineom.shared;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;

public class GwtPropHandler extends PropHandler {

	@Inject
	public GwtPropHandler() {
	}
	
	@Override
	public void setProp(HasProp o, String group, String key, Boolean value) {
		JSONObject groupJSON = getOrCreateJsonGroup(o, group);
		if (value == null) {
			groupJSON.put(key, JSONNull.getInstance());
		} else {
			groupJSON.put(key, JSONBoolean.getInstance(value));
		}
		o.setPropsJson(group, groupJSON.toString());
	}

	@Override
	public void setProp(HasProp o, String group, String key, Double value) {
		JSONObject groupJSON = getOrCreateJsonGroup(o, group);
		if (value == null) {
			groupJSON.put(key, JSONNull.getInstance());
		} else {
			groupJSON.put(key, new JSONNumber(value));
		}
		o.setPropsJson(group, groupJSON.toString());
	}

	@Override
	public void setProp(HasProp o, String group, String key, String value) {
		JSONObject groupJSON = getOrCreateJsonGroup(o, group);
		if (value == null) {
			groupJSON.put(key, JSONNull.getInstance());
		} else {
			groupJSON.put(key, new JSONString(value));
		}
		o.setPropsJson(group, groupJSON.toString());
	}
	
	private JSONObject getOrCreateJsonGroup(HasProp o, String group){
		JSONObject groupJSON = new JSONObject();
		if (o.getPropsJson(group) != null){
			groupJSON = JSONParser.parseStrict(o.getPropsJson(group)).isObject();
		}
		return groupJSON;
	}

	@Override
	public Boolean getBooleanProp(HasProp o, String group, String key) {
		try {
			return getProp(o, group, key).isBoolean().booleanValue();
		} catch (Exception e){
			return null;
		}
		
	}

	@Override
	public Double getNumberProp(HasProp ao, String group, String key) {
		try {
			return getProp(ao, group, key).isNumber().doubleValue();
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public String getStringProp(HasProp ao, String group, String key) {
		try {
			return getProp(ao, group, key).isString().stringValue();
		} catch (Exception e){
			return null;
		}
	}

	private JSONValue getProp(HasProp ao, String group, String key){
		if (ao.getPropsJson(group) == null) return null;
		JSONObject groupJSON = getOrCreateJsonGroup(ao, group);
		if (groupJSON.containsKey(key)){
			try {
				return groupJSON.get(key);
			} catch (Exception e){
				return null;
			}
		} else {
			return null;	
		}
	}

	@Override
	public String getStringPropFromGroupJson(String key, String json) {
		if(json == null) return null;
		JSONObject obj = JSONParser.parseStrict(json).isObject();
		if(obj != null){
			JSONString value = obj.get(key).isString();
			if(value != null){
				return value.stringValue();
			}
		}
		return null;
	}

	@Override
	public void setProp(HasProp hasProp, String group, String key,
			String value, boolean strictMatch) {
		setProp(hasProp, group, key, value);
		setProp(hasProp, group, getStrictMatchKey(key), strictMatch);
	}
}
