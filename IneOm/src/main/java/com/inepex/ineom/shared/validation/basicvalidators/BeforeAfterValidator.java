package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class BeforeAfterValidator implements KeyValueObjectValidator{
	
	private final String before;
	private final String after;
	private final String otherFieldsName;
	private final boolean msgOnFirst;
	
	public BeforeAfterValidator(String before, String after, boolean msgOnFirst, String nameOfIndex) {
		this.before=before;
		this.after=after;
		this.otherFieldsName=nameOfIndex;
		this.msgOnFirst=msgOnFirst;
	}

	@Override
	public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
		Long lb=kvo.getLong(before);
		Long la=kvo.getLong(after);
		
		if(lb==null || la==null) return;
		
		if(lb>=la) {
			if(msgOnFirst) {
				validationResult.addFieldError(before, IneOmI18n.validatorShouldBefore(otherFieldsName));
			} else {
				validationResult.addFieldError(before, IneOmI18n.validatorShouldAfter(otherFieldsName));
			}
		}
	}
}
