package com.inepex.ineForm.shared.types;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public enum ODFieldType implements IsSerializable  {
	
	BOOLEAN(IneT.BOOLEAN),
	DOUBLE(IneT.DOUBLE),
	LONG(IneT.LONG),
	STRING(IneT.STRING),
	EMAIL(IneT.STRING, KeyValueObjectValidationManager.EMAIL);
	
	public final IneT ineT;
	public final String[] validators;
	
	private ODFieldType(IneT ineT, String... validators) {
		this.ineT=ineT;
		this.validators=validators;
	}

	public static ODFieldType searchByFDesc(FDesc fd) {
		switch (fd.getType()) {
		case BOOLEAN:
			return BOOLEAN;
		case DOUBLE:
			return DOUBLE;
		case LONG:
			return LONG;
		case STRING:
			if(fd.hasValidator(KeyValueObjectValidationManager.EMAIL))
				return EMAIL;
			else
				return STRING;
		default:
			throw new IllegalArgumentException(fd.getType().toString());
		}
	}
	
	public static String getODFieldTypeName(ODFieldType oDFieldType) {
		switch (oDFieldType) {
		case BOOLEAN:
			return IneFormI18n.ODFieldType_BOOLEAN();
		case DOUBLE:
			return IneFormI18n.ODFieldType_DOUBLE();
		case LONG:
			return IneFormI18n.ODFieldType_LONG();
		case STRING:
			return IneFormI18n.ODFieldType_STRING();
		case EMAIL:
			return IneFormI18n.ODFieldType_EMAIL();
		}
		return null;
	}
}

