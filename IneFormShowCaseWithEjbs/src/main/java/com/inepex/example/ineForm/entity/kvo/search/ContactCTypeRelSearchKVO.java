package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactCTypeRelSearchKVO extends KeyValueObject {

	public static final String descriptorName = "contactCTypeRelSearchDescriptor";

	public ContactCTypeRelSearchKVO() {
		super(descriptorName);
	}

	public ContactCTypeRelSearchKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	
}
