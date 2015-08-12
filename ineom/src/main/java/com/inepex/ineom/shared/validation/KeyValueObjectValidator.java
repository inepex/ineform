package com.inepex.ineom.shared.validation;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface KeyValueObjectValidator {

    /**
     * if this method detects an error, just put it into the validationResult
     * using either the addGeneralError method or the addFieldError method with
     * the appropriate key
     */
    public void doValidation(AssistedObject kvo, ValidationResult validationResult);
}
