package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class BeforeAfterValidator implements KeyValueObjectValidator{
	
	private final AssistedObjectHandlerFactory objectHandlerFactory;
	
	private final String before;
	private final String after;
	private final String otherFieldsName;
	private final boolean msgOnFirst;
	
	public BeforeAfterValidator(String before, String after, boolean msgOnFirst, String nameOfIndex, AssistedObjectHandlerFactory objectHandlerFactory) {
		this.objectHandlerFactory=objectHandlerFactory;
		this.before=before;
		this.after=after;
		this.otherFieldsName=nameOfIndex;
		this.msgOnFirst=msgOnFirst;
	}

	@Override
	public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
		AssistedObjectHandler checker = objectHandlerFactory.createHandler(kvo);
		Long lb=checker.getLong(before);
		Long la=checker.getLong(after);
		
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
