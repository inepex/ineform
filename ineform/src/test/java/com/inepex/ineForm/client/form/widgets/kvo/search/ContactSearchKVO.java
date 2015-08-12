package com.inepex.ineForm.client.form.widgets.kvo.search;

import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;

public class ContactSearchKVO extends KeyValueObject {

    private static final long serialVersionUID = 1L;

    public static final String descriptorName = "contactSearchDescriptor";

    public static final String k_nationalities = "nationalities";

    public ContactSearchKVO() {
        super(descriptorName);
    }

    public ContactSearchKVO(KeyValueObject other) {
        super(descriptorName);
        if (other != null)
            if (descriptorName.equals(other.getDescriptorName()))
                // other.copyValuesTo(this);
                // TODO
                // TODO
                ;
    }

    public IneList getNationalities() {
        if (listValues == null)
            return null;
        return listValues.get(k_nationalities);
    }

    public void setNationalities(IneList nationalities) {
        set(k_nationalities, nationalities);
    }

}
