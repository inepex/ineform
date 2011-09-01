package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;


/**
 * 
 * @author SoTi
 *
 */
@SuppressWarnings("serial")
public class Prop implements Serializable{

	public static final String separator = ":";

	private String name;
	private String value;

	public static Prop fromString(String property) throws Exception{
		String[] parts = property.split("[" + separator + "]");
		
		if (parts.length == 1 && !parts[0].isEmpty())
			return new Prop(parts[0], "");
		
		if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty())
			throw new Exception("Invalid property format: \"" + property + "\"");

		return new Prop(parts[0], parts[1]);
	}
	
	/**
	 * for serialization
	 */
	public Prop() {
	}

	public Prop(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
