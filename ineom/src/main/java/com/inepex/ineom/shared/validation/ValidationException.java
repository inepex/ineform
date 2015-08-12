package com.inepex.ineom.shared.validation;

public class ValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    ValidationResult validationResult = null;

    public ValidationException() {}

    public ValidationException(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "ValidationException [validationResult=" + validationResult + "]";
    }
}
