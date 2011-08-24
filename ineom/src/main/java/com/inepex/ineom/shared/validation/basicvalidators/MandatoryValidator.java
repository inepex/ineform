package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory.AssistedObjectHandler;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class MandatoryValidator implements KeyValueObjectValidator {
	
	private final AssistedObjectHandlerFactory objectHandlerFactory;

	private final IneT type;
	private final String fieldName;
	
	public MandatoryValidator(IneT type, String fieldName, AssistedObjectHandlerFactory objectHandlerFactory) {
		this.objectHandlerFactory=objectHandlerFactory;
		this.type=type;
		this.fieldName=fieldName;
		
		if(type==IneT.UNDEFINED) 
			throw new RuntimeException("MandatoryValidator doesn't defined on type "+type.toString() + " defined on "+fieldName);
	}
	
	@Override
	public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
		String msg=null;
		AssistedObjectHandler checker = objectHandlerFactory.createHandler(kvo);
		
		switch (type) {
			case BOOLEAN:
				if(checker.getBoolean(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case DOUBLE:
				if(checker.getDouble(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case LIST:
				if(checker.getList(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case RELATION:
				if(checker.getRelation(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case LONG:
				if(checker.getLong(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case STRING:
				if(checker.getString(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
		}
		
		if(msg!=null) {
			validationResult.addFieldError(fieldName, msg);
		}
	}
}
