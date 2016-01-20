package com.inepex.ineom.shared.validation.basicvalidators;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class NumericRelationValidator implements KeyValueObjectValidator {

    private final String firstFieldName;
    private String secondFieldName;
    private Double constval;
    private final IneT type;
    private final RelType reltype;
    private final ObjectDesc objectDesc;

    private final String fieldDisplayname;

    public enum RelType implements IsSerializable {
        gt,
        lt,
        ge,
        le,
        eq;
    }

    public NumericRelationValidator(
        IneT type,
        String fieldName,
        String numberOrFieldname,
        RelType reltype,
        String fieldDisplayname,
        ObjectDesc objectDesc) {
        this.objectDesc = objectDesc;
        this.firstFieldName = fieldName;
        this.type = type;
        this.reltype = reltype;
        this.fieldDisplayname = fieldDisplayname;

        if (type != IneT.DOUBLE && type != IneT.LONG)
            throw new RuntimeException(
                "NumericRelationValidator on " + fieldName + " which type is " + type.toString());

        try {
            this.constval = Double.parseDouble(numberOrFieldname);
            this.secondFieldName = null;
        } catch (NumberFormatException e) {
            this.secondFieldName = numberOrFieldname;
            this.constval = null;
        }
    }

    @Override
    public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
        double firstVal = 0;
        double secondVal = 0;
        String constvalAsString = "";

        AssistedObjectChecker checker = new AssistedObjectChecker(
            kvo,
            kvo.getDescriptorName(),
            objectDesc);

        switch (type) {
            case DOUBLE:
                Double tmp = checker.getDouble(firstFieldName);
                if (tmp == null)
                    return;
                else
                    firstVal = tmp;

                if (secondFieldName != null) {
                    tmp = checker.getDouble(secondFieldName);
                    if (tmp == null)
                        return;
                    else
                        secondVal = tmp;
                } else {
                    secondVal = constval;
                }
                constvalAsString = Double.toString(secondVal);
                break;

            case LONG:
                Long tmp2 = checker.getLong(firstFieldName);
                if (tmp2 == null)
                    return;
                else
                    firstVal = tmp2;

                if (secondFieldName != null) {
                    tmp2 = checker.getLong(secondFieldName);
                    if (tmp2 == null)
                        return;
                    else
                        secondVal = tmp2;
                } else {
                    secondVal = constval;
                }

                constvalAsString = Long.toString(new Double(secondVal).longValue());
                break;
        }

        switch (reltype) {
            case eq:
                if (firstVal != secondVal)
                    validationResult.addFieldError(
                        firstFieldName,
                        IneOmI18n.validatorEQ(constvalAsString, fieldDisplayname));
                return;
            case gt:
                if (firstVal <= secondVal)
                    validationResult.addFieldError(
                        firstFieldName,
                        IneOmI18n.validatorGT(constvalAsString, fieldDisplayname));
                return;
            case lt:
                if (firstVal >= secondVal)
                    validationResult.addFieldError(
                        firstFieldName,
                        IneOmI18n.validatorLT(constvalAsString, fieldDisplayname));
                return;
            case ge:
                if (firstVal < secondVal)
                    validationResult.addFieldError(
                        firstFieldName,
                        IneOmI18n.validatorGE(constvalAsString, fieldDisplayname));
                return;
            case le:
                if (firstVal > secondVal)
                    validationResult.addFieldError(
                        firstFieldName,
                        IneOmI18n.validatorLE(constvalAsString, fieldDisplayname));
                return;

        }

    }

}
