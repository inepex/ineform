package com.inepex.ineom.shared;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class GwtPropHandler implements PropHandler {

	@Inject
	public GwtPropHandler() {
	}
	
	@Override
	public void setProp(AssistedObject ao, String group, String key, Boolean value) {
		JSONObject groupJSON = getOrCreateJsonGroup(ao, group);
		if (value == null) {
			groupJSON.put(key, JSONNull.getInstance());
		} else {
			groupJSON.put(key, JSONBoolean.getInstance(value));
		}
		ao.setPropsJson(group, groupJSON.toString());
	}

	@Override
	public void setProp(AssistedObject ao, String group, String key, Double value) {
		JSONObject groupJSON = getOrCreateJsonGroup(ao, group);
		if (value == null) {
			groupJSON.put(key, JSONNull.getInstance());
		} else {
			groupJSON.put(key, new JSONNumber(value));
		}
		ao.setPropsJson(group, groupJSON.toString());
	}

	@Override
	public void setProp(AssistedObject ao, String group, String key, String value) {
		JSONObject groupJSON = getOrCreateJsonGroup(ao, group);
		if (value == null) {
			groupJSON.put(key, JSONNull.getInstance());
		} else {
			groupJSON.put(key, new JSONString(value));
		}
		ao.setPropsJson(group, groupJSON.toString());
	}
	
	private JSONObject getOrCreateJsonGroup(AssistedObject ao, String group){
		JSONObject groupJSON = new JSONObject();
		if (ao.getPropsJson(group) != null){
			groupJSON = JSONParser.parseStrict(ao.getPropsJson(group)).isObject();
		}
		return groupJSON;
	}

	@Override
	public Boolean getBooleanProp(AssistedObject ao, String group, String key) {
		try {
			return getProp(ao, group, key).isBoolean().booleanValue();
		} catch (Exception e){
			return null;
		}
		
	}

	@Override
	public Double getNumberProp(AssistedObject ao, String group, String key) {
		try {
			return getProp(ao, group, key).isNumber().doubleValue();
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public String getStringProp(AssistedObject ao, String group, String key) {
		try {
			return getProp(ao, group, key).isString().stringValue();
		} catch (Exception e){
			return null;
		}
	}

	private JSONValue getProp(AssistedObject ao, String group, String key){
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
}
