package com.inepex.ineForm.client.form.widgets.kvo.search;

import com.inepex.ineom.shared.assistedobject.KeyValueObject;

public class ContactNatRelSearchKVO extends KeyValueObject {

    private static final long serialVersionUID = 1L;

    public static final String descriptorName = "contactNatRelSearchDescriptor";

    public ContactNatRelSearchKVO() {
        super(descriptorName);
    }

    public ContactNatRelSearchKVO(KeyValueObject other) {
        super(descriptorName);
        if (other != null)
            if (descriptorName.equals(other.getDescriptorName()))
                // other.copyValuesTo(this);
                // TODO
                // TODO
                // TODO
                ;
    }

}
