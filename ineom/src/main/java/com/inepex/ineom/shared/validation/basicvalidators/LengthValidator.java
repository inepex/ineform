package com.inepex.ineom.shared.validation.basicvalidators;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class LengthValidator implements KeyValueObjectValidator {

    public enum Type implements IsSerializable {
        MAXLENGTH,
        MINLENGTH
    }

    private final ObjectDesc objectDesc;

    private final String fieldName;
    private final int limit;
    private final Type type;

    public LengthValidator(
        IneT fieldType,
        String fieldName,
        Type type,
        int limit,
        ObjectDesc objectDesc) {
        this.objectDesc = objectDesc;
        this.limit = limit;
        this.fieldName = fieldName;
        this.type = type;

        if (fieldType != IneT.STRING)
            throw new RuntimeException("LengthValidator doesn't defined on type "
                + fieldType.toString()
                + " defined on "
                + fieldName);
    }

    @Override
    public void doValidation(AssistedObject kvo, ValidationResult validationResult) {

        String val =
            new AssistedObjectChecker(kvo, kvo.getDescriptorName(), objectDesc)
                .getString(fieldName);
        if (val != null) {
            if (type == Type.MAXLENGTH && val.length() > limit) {
                validationResult.addFieldError(
                    fieldName,
                    IneOmI18n.validatorMaxLength(Integer.toString(limit)));
            } else if (type == Type.MINLENGTH && val.length() < limit) {
                validationResult.addFieldError(
                    fieldName,
                    IneOmI18n.validatorMinLength(Integer.toString(limit)));
            }
        }
    }

}
