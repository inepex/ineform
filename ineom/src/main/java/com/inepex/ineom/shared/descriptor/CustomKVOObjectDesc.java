package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;


@SuppressWarnings("serial")
public class CustomKVOObjectDesc extends ObjectDesc implements Serializable{
	
	/**
	 *  field key of ObjectDec
	 */
	private String key;
	
	
	public CustomKVOObjectDesc() {	
	}
	
	public CustomKVOObjectDesc(String customdescriptorname) {
		super(customdescriptorname);
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
