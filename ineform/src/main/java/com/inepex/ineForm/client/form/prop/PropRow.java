package com.inepex.ineForm.client.form.prop;

import com.inepex.ineForm.shared.types.PropFieldType;

public class PropRow {
	private static long nextId = 0L;

	private Long innerId = nextId++;

	private String key;
	private PropFieldType type;
	private String value;

	/**
	 * creating from existing values
	 */
	public PropRow(String key, PropFieldType type, String value) {
		this.key = key;
		this.type = type;
		this.value = value;
	}

	/**
	 * creating empty row
	 */
	public PropRow() {
		key = "";
		type = PropFieldType.STRING;
		value = "";
	}

	public Long getInnerId() {
		return innerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public PropFieldType getType() {
		return type;
	}

	public void setType(PropFieldType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void clearDataIfCanNotParse() {
		if(type==PropFieldType.BOOLEAN) {
			try {
				Boolean.parseBoolean(value);
			} catch (NumberFormatException e) {
				value="";
			}
			
			return;
		}
		
		
		if(type==PropFieldType.DOUBLE) {
			try {
				Double.parseDouble(value);
			} catch (NumberFormatException e) {
				value="";
			}
			
			return;
		}
	}
}