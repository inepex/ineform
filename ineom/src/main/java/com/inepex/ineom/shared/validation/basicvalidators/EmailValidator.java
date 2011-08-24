package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class EmailValidator implements KeyValueObjectValidator {
	
	private final AssistedObjectHandlerFactory objectHandlerFactory;
	
	/**
	 * from IneFormBuilder: com.inepex.formbuilder.validator.EmailValidator revision:1840
	 */
	private final String regExpr = "^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,6}$";
	private final String fieldName;

	public EmailValidator(IneT type, String fieldName, AssistedObjectHandlerFactory objectHandlerFactory) {
		this.objectHandlerFactory=objectHandlerFactory;
		this.fieldName=fieldName;
		
		if(type!=IneT.STRING) 
			throw new RuntimeException("EmailValidator doesn't defined on type "+type.toString() + " defined on "+fieldName);
	}

	@Override
	public void doValidation(AssistedObject kvo,
			ValidationResult validationResult) {
		
		String val = objectHandlerFactory.createChecker(kvo).getString(fieldName);
		if(val!=null && val.replaceAll(regExpr, "").length()!=0) {
			validationResult.addFieldError(fieldName, IneOmI18n.validationEmail());
		}
	}
	

}
