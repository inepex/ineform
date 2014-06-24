package com.inepex.ineom.shared;

import java.util.HashMap;
import java.util.Map;

public class HashMapThatImplementsHasProp implements HasProp {

	private final Map<String, String> map = new HashMap<>();
	
	@Override
	public Map<String, String> getAllPropsJson() {
		return map;
	}

	@Override
	public String getPropsJson(String id) {
		return map.get(id);
	}

	@Override
	public void setPropsJson(String id, String json) {
		map.put(id, json);
	}

}
