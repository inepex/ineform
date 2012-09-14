package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class DescriptorBase implements Serializable, IsSerializable {
	
	public static final String separator = ":";
	
	protected Map<String, String> props = new TreeMap<String, String>();
	private String displayName = null;
	
	public void addProps(String... props) {
		if(props!=null) {
			for(String prop : props)
				addProp(prop);
		}
		
	}
	
	public void addProps(Map<String, String> props) {
		props.putAll(props);
	}
	
	public DescriptorBase addProp(String prop) {
		String[] parts = parseProp(prop);
		addProp(parts[0], parts[1]);
		return this;
	}
	
	public static String[] parseProp(String prop) {
		String[] parts = prop.split("[" + separator + "]");
		
		if (parts.length == 1 && !parts[0].isEmpty())
			return new String[]{parts[0], ""};

		if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty())
			throw new RuntimeException("Invalid property format: \"" + prop + "\"");

		return parts;
	}
	
	public DescriptorBase addProp(String name, String value) {
		props.put(name, value);
		return this;
	}	
	
	public Map<String, String> getProps() {
		return props;
	}
	
	public boolean hasProp(String name){
		return props.containsKey(name);
	}
	
	public String getPropValue(String name){
		return props.get(name);
	}

	public String getDisplayName() {
		return displayName;
	}

	@SuppressWarnings("unchecked")
	public <T extends DescriptorBase> T setDisplayName(String displayName, Class<T> clazz) {
		this.displayName = displayName;
		return (T) this;
	}
}
