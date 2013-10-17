package com.inepex.ineom.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.inject.Inject;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class JavaPropHandler implements PropHandler {

	private static final Logger _logger = LoggerFactory.getLogger(JavaPropHandler.class);

	@Inject
	public JavaPropHandler() {
	}

	@Override
	public void setProp(AssistedObject ao, String group, String key, Boolean value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(ao, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else {
				groupJSON.put(key, BooleanNode.valueOf(value));
			}
			ao.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(ao, group, key, value);
		}
	}

	@Override
	public void setProp(AssistedObject ao, String group, String key, Double value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(ao, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else {
				groupJSON.put(key, DoubleNode.valueOf(value));
			}
			ao.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(ao, group, key, value);
		}
	}

	@Override
	public void setProp(AssistedObject ao, String group, String key, String value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(ao, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else {
				groupJSON.put(key, TextNode.valueOf(value));
			}
			ao.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(ao, group, key, value);
		}
	}

	private ObjectNode getOrCreateJsonGroup(AssistedObject ao, String group) throws Exception {
		ObjectMapper m = new ObjectMapper();
		ObjectNode groupJSON = (ObjectNode) m.readTree("{}");
		if (ao.getPropsJson(group) != null) {
			groupJSON = (ObjectNode) m.readTree(ao.getPropsJson(group));
		}
		return groupJSON;
	}
	
	private void logSetProp(AssistedObject ao, String group, String key, Object value){
		_logger.error(
				"Failed to set prop ao: {} group: {} key: {}, value: {}",
				new Object[] { ao.getId(), group, key, value });
	}

	@Override
	public Boolean getBooleanProp(AssistedObject ao, String group, String key) {
		if (ao.getPropsJson(group) == null) return null;
		try {
			ObjectMapper m = new ObjectMapper();
			ObjectNode groupJSON = (ObjectNode) m.readTree(ao.getPropsJson(group));
			if (groupJSON.has(key)){
				return groupJSON.get(key).asBoolean();
			} else {
				return null;
			}
		} catch (Exception e){
			logGetProp(ao, group, key);
			return null;
		}
	}

	@Override
	public Double getNumberProp(AssistedObject ao, String group, String key) {
		if (ao.getPropsJson(group) == null) return null;
		try {
			ObjectMapper m = new ObjectMapper();
			ObjectNode groupJSON = (ObjectNode) m.readTree(ao.getPropsJson(group));
			if (groupJSON.has(key)){
				return groupJSON.get(key).asDouble();
			} else {
				return null;
			}
		} catch (Exception e){
			logGetProp(ao, group, key);
			return null;
		}
	}

	@Override
	public String getStringProp(AssistedObject ao, String group, String key) {
		if (ao.getPropsJson(group) == null) return null;
		try {
			ObjectMapper m = new ObjectMapper();
			ObjectNode groupJSON = (ObjectNode) m.readTree(ao.getPropsJson(group));
			if (groupJSON.has(key)){
				return groupJSON.get(key).asText();
			} else {
				return null;
			}
		} catch (Exception e){
			logGetProp(ao, group, key);
			return null;
		}
	}

	private void logGetProp(AssistedObject ao, String group, String key){
		_logger.error(
				"Failed to set prop ao: {} group: {} key: {}",
				new Object[] { ao.getId(), group, key});
	}
}
