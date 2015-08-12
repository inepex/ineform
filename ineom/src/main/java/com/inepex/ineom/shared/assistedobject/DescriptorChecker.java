package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public abstract class DescriptorChecker {

    protected final ObjectDesc objectDescriptor;
    protected final String desciptorName;

    protected DescriptorChecker(ObjectDesc objectDescriptor, String desciptorName) {
        this.desciptorName = desciptorName;
        this.objectDescriptor = objectDescriptor;
    }

    public abstract void checkDescriptorCheckKey(String key, IneT ineT);
}
