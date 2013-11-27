package com.inepex.ineom.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.inject.Inject;
import com.inepex.ineom.shared.HasProp;
import com.inepex.ineom.shared.PropHandler;

public class JavaPropHandler extends PropHandler {

	private static final Logger _logger = LoggerFactory.getLogger(JavaPropHandler.class);

	@Inject
	public JavaPropHandler() {
	}

	@Override
	public void setProp(HasProp o, String group, String key, Boolean value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(o, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else {
				groupJSON.put(key, BooleanNode.valueOf(value));
			}
			o.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(o, group, key, value);
		}
	}

	@Override
	public void setProp(HasProp o, String group, String key, Double value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(o, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else {
				groupJSON.put(key, DoubleNode.valueOf(value));
			}
			o.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(o, group, key, value);
		}
	}

	@Override
	public void setProp(HasProp o, String group, String key, String value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(o, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else {
				groupJSON.put(key, TextNode.valueOf(value));
			}
			o.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(o, group, key, value);
		}
	}

	private ObjectNode getOrCreateJsonGroup(HasProp o, String group) throws Exception {
		ObjectMapper m = new ObjectMapper();
		ObjectNode groupJSON = (ObjectNode) m.readTree("{}");
		if (o.getPropsJson(group) != null) {
			groupJSON = (ObjectNode) m.readTree(o.getPropsJson(group));
		}
		return groupJSON;
	}
	
	private void logSetProp(HasProp o, String group, String key, Object value){
		_logger.error(
				"Failed to set prop ao: {} group: {} key: {}, value: {}",
				new Object[] { o.toString(), group, key, value });
	}

	@Override
	public Boolean getBooleanProp(HasProp o, String group, String key) {
		if (o.getPropsJson(group) == null) return null;
		try {
			ObjectMapper m = new ObjectMapper();
			ObjectNode groupJSON = (ObjectNode) m.readTree(o.getPropsJson(group));
			if (groupJSON.has(key)){
				return groupJSON.get(key).asBoolean();
			} else {
				return null;
			}
		} catch (Exception e){
			logGetProp(o, group, key);
			return null;
		}
	}

	@Override
	public Double getNumberProp(HasProp o, String group, String key) {
		if (o.getPropsJson(group) == null) return null;
		try {
			ObjectMapper m = new ObjectMapper();
			ObjectNode groupJSON = (ObjectNode) m.readTree(o.getPropsJson(group));
			if (groupJSON.has(key)){
				return groupJSON.get(key).asDouble();
			} else {
				return null;
			}
		} catch (Exception e){
			logGetProp(o, group, key);
			return null;
		}
	}

	@Override
	public String getStringProp(HasProp o, String group, String key) {
		if (o.getPropsJson(group) == null) return null;
		try {
			ObjectMapper m = new ObjectMapper();
			ObjectNode groupJSON = (ObjectNode) m.readTree(o.getPropsJson(group));
			if (groupJSON.has(key)){
				return groupJSON.get(key).asText();
			} else {
				return null;
			}
		} catch (Exception e){
			logGetProp(o, group, key);
			return null;
		}
	}

	private void logGetProp(HasProp o, String group, String key){
		_logger.error(
				"Failed to set prop ao: {} group: {} key: {}",
				new Object[] { o.toString(), group, key});
	}

	@Override
	public String getStringPropFromGroupJson(String key, String json) {
		ObjectMapper m = new ObjectMapper();
		try {
			JsonNode node = m.readTree(json);
			JsonNode valueNode = node.get(key);
			if(valueNode != null){
				return valueNode.asText();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public void setProp(HasProp hasProp, 
						String group, 
						String key,
						String value, 
						boolean strictMatch) {
		setProp(hasProp, group, key, value);
		setProp(hasProp, group, getStrictMatchKey(key), strictMatch);
	}

	@Override
	public Map<String, Object> getPropMap(HasProp hasProp, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (hasProp.getPropsJson(id) == null) return map;
		ObjectMapper m = new ObjectMapper();
		ObjectNode groupJSON;
		try {
			groupJSON = (ObjectNode) m.readTree(hasProp.getPropsJson(id));
			Iterator<Entry<String, JsonNode>> fieldIter = groupJSON.fields();
			while(fieldIter.hasNext()){
				Entry<String, JsonNode> entry = fieldIter.next();
				String key = entry.getKey();
				JsonNode node = entry.getValue();
				if(node.isInt()){
					map.put(key, node.intValue());
				}else if(node.isBoolean()){
					map.put(key, node.booleanValue());
				}else if(node.isDouble()){
					map.put(key, node.doubleValue());
				}else if(node.isLong()){
					map.put(key, node.longValue());
				}else if(node.isNumber()){
					map.put(key, node.booleanValue());
				}else if(node.isTextual()){
					map.put(key, node.textValue());
				}
			}
		} catch (Exception e) {
			logGetProp(hasProp, id, "");
		}
		return map;
	}

	@Override
	public void setProp(HasProp hasProp, String group, String key, Object value) {
		try {
			ObjectNode groupJSON = getOrCreateJsonGroup(hasProp, group);
			if (value == null) {
				groupJSON.put(key, NullNode.getInstance());
			} else if(value instanceof Long){
				groupJSON.put(key, LongNode.valueOf((Long)value));
			}else if(value instanceof Double){
				groupJSON.put(key, DoubleNode.valueOf((Double)value));
			}else if(value instanceof Integer){
				groupJSON.put(key, IntNode.valueOf((Integer)value));
			}else if(value instanceof Float){
				groupJSON.put(key, new DoubleNode((double)value));
			}else if(value instanceof String){
				groupJSON.put(key, TextNode.valueOf((String)value));
			}else if(value instanceof Boolean){
				groupJSON.put(key, BooleanNode.valueOf((Boolean)value));
			}
			hasProp.setPropsJson(group, groupJSON.toString());
		} catch (Exception e) {
			logSetProp(hasProp, group, key, value);
		}
	}
}
