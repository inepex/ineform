package com.inepex.ineForm.shared.types;

import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public enum ODFieldType {
	
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
		}
		
		throw new IllegalArgumentException(fd.getType().toString());
	}
}

