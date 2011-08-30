package com.inepex.ineForm.shared.customkvo;

import com.inepex.ineom.shared.assistedobject.KeyValueObject;

@SuppressWarnings("serial")
public class UncheckedKVO extends KeyValueObject {

	public UncheckedKVO(String descriptorName) {
		super(descriptorName);
	}
	
	@Override
	public void set(String key, Boolean value) {
		super.set(key, value);
	}
	
	@Override
	public void set(String key, Double value) {
		super.set(key, value);
	}
	
	@Override
	public void set(String key, Long value) {
		super.set(key, value);
	}
	
	@Override
	public void set(String key, String value) {
		super.set(key, value);
	}
	
}