package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class MandatoryValidator implements KeyValueObjectValidator {

	private final IneT type;
	private final String fieldName;
	
	public MandatoryValidator(IneT type, String fieldName) {
		this.type=type;
		this.fieldName=fieldName;
		
		if(type==IneT.UNDEFINED) 
			throw new RuntimeException("MandatoryValidator doesn't defined on type "+type.toString() + " defined on "+fieldName);
	}
	
	@Override
	public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
		String msg=null;
		
		switch (type) {
			case BOOLEAN:
				if(kvo.getBoolean(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case DOUBLE:
				if(kvo.getDouble(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case LIST:
				if(kvo.getList(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case RELATION:
				if(kvo.getRelation(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case LONG:
				if(kvo.getLong(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
			case STRING:
				if(kvo.getString(fieldName)==null)  msg = IneOmI18n.validator_mandatory(); 
				break;
		}
		
		if(msg!=null) {
			validationResult.addFieldError(fieldName, msg);
		}
	}
}
