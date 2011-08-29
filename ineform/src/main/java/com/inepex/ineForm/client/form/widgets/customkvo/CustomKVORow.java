package com.inepex.ineForm.client.form.widgets.customkvo;

import com.inepex.ineForm.shared.types.ODFieldType;

public class CustomKVORow {
	private static int nextId = 0;

	private Integer innerId = nextId++;

	private String key;
	private ODFieldType type;
	private String value;

	/**
	 * creating from existing values
	 */
	public CustomKVORow(String key, ODFieldType type, String value) {
		this.key = key;
		this.type = type;
		this.value = value;
	}

	/**
	 * creating empty row
	 */
	public CustomKVORow() {
		key = "";
		type = ODFieldType.values()[0];
		value = "";
	}

	public Integer getInnerId() {
		return innerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ODFieldType getType() {
		return type;
	}

	public void setType(ODFieldType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}