package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class LengthValidator implements KeyValueObjectValidator {

	private final String fieldName;
	private final int maxLength;

	public LengthValidator(IneT type, String fieldName, int maxLength) {
		this.maxLength=maxLength;
		this.fieldName=fieldName;
		
		if(type!=IneT.STRING) 
			throw new RuntimeException("LengthValidator doesn't defined on type "+type.toString() + " defined on "+fieldName);
	}

	@Override
	public void doValidation(AssistedObject kvo,
			ValidationResult validationResult) {
		
		String val = kvo.getString(fieldName);
		if(val!=null && val.length()>maxLength) {
			validationResult.addFieldError(fieldName, IneOmI18n.validatorLength(Integer.toString(maxLength)));
		}
	}
	

}
