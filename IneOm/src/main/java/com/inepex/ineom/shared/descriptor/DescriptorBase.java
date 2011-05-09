package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class DescriptorBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4902725042402080029L;
	protected Map<String, Prop> props = new TreeMap<String,Prop>();

	private String displayName = null;
	
	public void addProps(Map<String, Prop> props) {
		props.putAll(props);
	}
	
	public void addProps(String... propList) {
		for (String propString : propList){
			addProp(propString);
		}
	}
	
	public void addProp(Prop p) {
		props.put(p.getName(), p);
	}
	
	public void addProp(String prop) {
		try {
			Prop p =Prop.fromString(prop);
			props.put(p.getName(), p);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}
	
	public void addProp(String name, String value) {
		addProp(new Prop(name, value));
	}	
	
	public Map<String, Prop> getProps() {
		return props;
	}
	
	public boolean hasProp(String name){
		return props.containsKey(name);
	}
	
	public String getPropValue(String name){
		if (props.get(name) != null)
			return props.get(name).getValue();
		else return null;
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
