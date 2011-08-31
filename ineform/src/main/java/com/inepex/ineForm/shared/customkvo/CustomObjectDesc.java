package com.inepex.ineForm.shared.customkvo;

import com.inepex.ineom.shared.descriptor.ObjectDesc;

@SuppressWarnings("serial")
public class CustomObjectDesc extends ObjectDesc{
	
	/**
	 *  field key of ObjectDec
	 */
	private String key;
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
