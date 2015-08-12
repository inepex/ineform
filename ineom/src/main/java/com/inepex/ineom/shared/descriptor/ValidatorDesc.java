package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ValidatorDesc extends DescriptorBase implements Serializable, IsSerializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3350119608950432751L;

    private String descName;
    private String[] validatorNames;

    /**
     * for serialization
     */
    public ValidatorDesc() {}

    public ValidatorDesc(String descName, String[] validatorNames) {
        this.descName = descName;
        this.validatorNames = validatorNames;
    }

    public String getObjectDescriptorName() {
        return descName;
    }

    public String[] getValidatorNames() {
        return validatorNames;
    }
}
