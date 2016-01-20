package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class RealDescriptorChecker extends DescriptorChecker {

    public RealDescriptorChecker(ObjectDesc objectDescriptor, String desciptorName) {
        super(objectDescriptor, desciptorName);
    }

    @Override
    public void checkDescriptorCheckKey(String key, IneT ineT) {
        if (!objectDescriptor.containsKey(key))
            throw new InvalidKeyException(
                "Key '" + key + "' is not found in descriptor '" + desciptorName + "'");

        IneT fieldT = objectDescriptor.getField(key).getType();
        if (!fieldT.equals(ineT))
            throw new InvalidKeyException(
                "The type of field for key '" + key + "' in descriptor '" + desciptorName + "' is '"
                    + fieldT.toString() + "' and not '" + ineT.toString() + "'");
    }

}
