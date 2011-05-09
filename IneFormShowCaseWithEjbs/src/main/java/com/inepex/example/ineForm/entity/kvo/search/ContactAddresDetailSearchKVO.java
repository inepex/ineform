package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactAddresDetailSearchKVO extends KeyValueObject {

	public static final String descriptorName = "contactAddresDetailSearchDescriptor";

	public ContactAddresDetailSearchKVO() {
		super(descriptorName);
	}

	public ContactAddresDetailSearchKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	
}
