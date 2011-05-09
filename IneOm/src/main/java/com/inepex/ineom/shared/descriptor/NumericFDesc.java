package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

@SuppressWarnings("serial")
public abstract class NumericFDesc extends FDesc implements Serializable {

	public NumericFDesc(String key, IneT type, String defaultDisplayName, String... properties) {
		super(key, type, defaultDisplayName, properties);
	}

	public NumericFDesc(String key, IneT type, String... properties) {
		super(key, type, properties);
	}

	public NumericFDesc(String key, IneT type, String defaultDisplayName) {
		super(key, type, defaultDisplayName);
	}

	public NumericFDesc(String key, IneT type) {
		super(key, type);
	}
	
	public NumericFDesc() {
	}
	
	public NumericFDesc gt(String field) {
		validatorNames.add(KeyValueObjectValidationManager.GREATERTHEN+":"+field);
		return this;
	}
	
	public NumericFDesc lt(String field) {
		validatorNames.add(KeyValueObjectValidationManager.LESSTHEN+":"+field);
		return this;
	}
	
	public NumericFDesc gtOrEqual(String field) {
		validatorNames.add(KeyValueObjectValidationManager.GREATEREQUAL+":"+field);
		return this;
	}
	
	public NumericFDesc ltOrEqual(String field) {
		validatorNames.add(KeyValueObjectValidationManager.LESSEQUAL+":"+field);
		return this;
	}
	
	public NumericFDesc eq(String field) {
		validatorNames.add(KeyValueObjectValidationManager.EQUAL+":"+field);
		return this;
	}
	
	
	public NumericFDesc gt(long value) {
		return gt(""+value);
	}
	
	public NumericFDesc lt(long value) {
		return lt(""+value);
	}
	
	public NumericFDesc gtOrEqual(long value) {
		return gtOrEqual(""+value);
	}
	
	public NumericFDesc ltOrEqual(long value) {
		return ltOrEqual(""+value);
	}
	
	public NumericFDesc eq(long value) {
		return eq(""+value);
	}
	
	public NumericFDesc gt(double value) {
		return gt(""+value);
	}
	
	public NumericFDesc lt(double value) {
		return lt(""+value);
	}
	
	public NumericFDesc gtOrEqual(double value) {
		return gtOrEqual(""+value);
	}
	
	public NumericFDesc ltOrEqual(double value) {
		return ltOrEqual(""+value);
	}
	
	public NumericFDesc eq(double value) {
		return eq(""+value);
	}
}
