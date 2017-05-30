package com.inepex.ineom.shared.descriptor.fdesc;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

import java.io.Serializable;

/**
 * @author Istvan Szoboszlai
 *
 */
public class StringFDesc extends FDesc implements Serializable, IsSerializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5521900795365354819L;

    public StringFDesc() {
        type = IneT.STRING;
    }

    public StringFDesc(String key, String defaultDisplayName, String... props) {
        super(key, IneT.STRING, defaultDisplayName, props);
    }

    public StringFDesc(String key, String defaultDisplayName) {
        super(key, IneT.STRING, defaultDisplayName);
    }

    public StringFDesc maxLength(int maxLength) {
        validatorNames.add(KeyValueObjectValidationManager.MAXLENGTH + ":" + maxLength);
        return this;
    }

    public StringFDesc minLength(int minLength) {
        validatorNames.add(KeyValueObjectValidationManager.MINLENGTH + ":" + minLength);
        return this;
    }

    public StringFDesc email() {
        validatorNames.add(KeyValueObjectValidationManager.EMAIL);
        return this;
    }

    public StringFDesc alphanum() {
        validatorNames.add(KeyValueObjectValidationManager.ALPHANUM);
        return this;
    }

    public StringFDesc alphanumOrSpace() {
        validatorNames.add(KeyValueObjectValidationManager.ALPHANUM_OR_SPACE);
        return this;
    }

    @Override
    public StringFDesc addProp(String name, String value) {
        super.addProp(name, value);
        return this;
    }
}
