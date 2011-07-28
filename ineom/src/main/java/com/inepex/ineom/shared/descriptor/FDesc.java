package com.inepex.ineom.shared.descriptor;

import java.util.TreeSet;

import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public abstract class FDesc extends DescriptorBase {

	/**
	 *
	 */
	private static final long serialVersionUID = -6754736044439157370L;

	private String key;

	// the field can be edited
	private boolean editable = true;

	protected final TreeSet<String> validatorNames = new TreeSet<String>();

	protected IneT type = null;

	private String defaultDisplayName = null;

	private boolean nullable = true;

	public FDesc() {

	}

	public FDesc(String key, IneT type) {
		this.key = key;
		this.type = type;
	}

	public FDesc(String key, IneT type, String defaultDisplayName) {
		this.key = key;
		this.type = type;
		this.defaultDisplayName = defaultDisplayName;
	}

	public FDesc(String key, IneT type, String defaultDisplayName,
			String... properties) {
		this.key = key;
		this.type = type;
		this.defaultDisplayName = defaultDisplayName;
		addProps(properties);
	}

	public FDesc(String key, IneT type, String... properties) {
		this.key = key;
		this.type = type;
		addProps(properties);
	}

	public FDesc addValidators(String... names) {
		for (String name : names) {
			validatorNames.add(name);
		}

		return this;
	}

	public TreeSet<String> getValidatorNames() {
		return validatorNames;
	}

	public boolean hasValidator(String validatorName) {
		return validatorNames.contains(validatorName);
	}

	public String getKey() {
		return key;
	}

	public boolean isEditable() {
		return editable;
	}

	public FDesc setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	public IneT getType() {
		return type;
	}

	public String getDefaultDisplayName() {
		return defaultDisplayName;
	}

	public void setDefaultDisplayName(String defaultDisplayName) {
		this.defaultDisplayName = defaultDisplayName;
	}

	public boolean isNullable() {
		return nullable;
	}

	public FDesc setNullable(boolean nullable) {
		this.nullable = nullable;
		return this;
	}

	public FDesc mandatory() {
		validatorNames.add(KeyValueObjectValidationManager.MANDATORY);
		return this;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
