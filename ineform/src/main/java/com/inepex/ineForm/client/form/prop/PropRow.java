package com.inepex.ineForm.client.form.prop;

import com.inepex.ineForm.shared.types.ODFieldType;

public class PropRow {
	private static long nextId = 0L;

	private Long innerId = nextId++;

	private String key;
	private ODFieldType type;
	private String value;

	/**
	 * creating from existing values
	 */
	public PropRow(String key, ODFieldType type, String value) {
		this.key = key;
		this.type = type;
		this.value = value;
	}

	/**
	 * creating empty row
	 */
	public PropRow() {
		key = "";
		type = ODFieldType.STRING;
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

	public void clearDataIfCanNotParse() {
		if(type==ODFieldType.BOOLEAN) {
			try {
				Boolean.parseBoolean(value);
			} catch (NumberFormatException e) {
				value="";
			}
			
			return;
		}
		
		if(type==ODFieldType.LONG) {
			try {
				Long.parseLong(value);
			} catch (NumberFormatException e) {
				value="";
			}
			
			return;
		}
		
		if(type==ODFieldType.DOUBLE) {
			try {
				Double.parseDouble(value);
			} catch (NumberFormatException e) {
				value="";
			}
			
			return;
		}
	}
}