package com.inepex.ineom.shared.validation.basicvalidators;

import com.google.gwt.core.shared.GWT;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class RegexprValidator implements KeyValueObjectValidator {

    private final ObjectDesc objectDesc;

    private final String fieldName;
    private final String regExprJAVA;
    private final String regExprJS;
    private final String errorMsg;

    public RegexprValidator(
        IneT type,
        String fieldName,
        ObjectDesc objectDesc,
        String regExprJAVA,
        String regExprJS,
        String errorMsg) {
        this.objectDesc = objectDesc;
        this.fieldName = fieldName;
        this.errorMsg = errorMsg;
        this.regExprJAVA = regExprJAVA;
        this.regExprJS = regExprJS;

        if (type != IneT.STRING)
            throw new RuntimeException("RegexprValidator doesn't defined on type "
                + type.toString()
                + " defined on "
                + fieldName);
    }

    @Override
    public void doValidation(AssistedObject kvo, ValidationResult validationResult) {

        String val =
            new AssistedObjectChecker(kvo, kvo.getDescriptorName(), objectDesc)
                .getString(fieldName);
        if (val == null || "".equals(val))
            return;

        if (GWT.isClient()) {
            if (!val.matches(regExprJS)) {
                validationResult.addFieldError(fieldName, errorMsg);
            }
        } else {
            if (!val.matches(regExprJAVA)) {
                validationResult.addFieldError(fieldName, errorMsg);
            }
        }
    }
}
