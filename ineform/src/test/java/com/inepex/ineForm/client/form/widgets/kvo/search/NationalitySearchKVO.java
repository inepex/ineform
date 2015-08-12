package com.inepex.ineForm.client.form.widgets.kvo.search;

import com.inepex.ineom.shared.assistedobject.KeyValueObject;

public class NationalitySearchKVO extends KeyValueObject {

    private static final long serialVersionUID = 1L;

    public static final String descriptorName = "nationalitySearchDescriptor";

    public NationalitySearchKVO() {
        super(descriptorName);
    }

    public NationalitySearchKVO(KeyValueObject other) {
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
