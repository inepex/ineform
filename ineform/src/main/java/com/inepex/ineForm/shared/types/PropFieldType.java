package com.inepex.ineForm.shared.types;

import com.inepex.ineForm.client.i18n.IneFormI18n;


public enum PropFieldType {
	BOOLEAN,
	DOUBLE,
	STRING;
	
	public static String getName(PropFieldType propFieldType) {
		switch (propFieldType) {
		case BOOLEAN:
			return IneFormI18n.ODFieldType_BOOLEAN();
		case DOUBLE:
			return IneFormI18n.ODFieldType_DOUBLE();
		case STRING:
			return IneFormI18n.ODFieldType_STRING();
		}
		return null;
	}
}
