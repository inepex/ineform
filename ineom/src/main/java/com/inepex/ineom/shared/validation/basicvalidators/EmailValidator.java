package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class EmailValidator implements KeyValueObjectValidator {

    private final ObjectDesc objectDesc;

    public final static String regExpr = "^" + "([a-zA-Z0-9]){1,40}" + // firstname
        "(([_\\.\\-\\+])([a-zA-Z0-9]){1,40})*"
        + // .lastname or .lastname.lastlastname...
        "@"
        + // @
        "([a-zA-Z0-9]){1,40}"
        + // sg
        "(([_\\.\\-\\+])([a-zA-Z0-9]){1,40})*"
        + // -domain or -domain-domain...
        "\\."
        + // .
        "[a-zA-Z0-9]{2,6}$"; // com

    private final String fieldName;

    public EmailValidator(IneT type, String fieldName, ObjectDesc objectDesc) {
        this.objectDesc = objectDesc;
        this.fieldName = fieldName;

        if (type != IneT.STRING)
            throw new RuntimeException("EmailValidator doesn't defined on type "
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

        if (!isValidEmail(val)) {
            validationResult.addFieldError(fieldName, IneOmI18n.validationEmail());
        }
    }

    private static boolean isValidEmail(String email) {
        return email.matches(regExpr);
    }

    public static boolean isNotNullAndValid(String email) {
        return email != null && isValidEmail(email);
    }

}
