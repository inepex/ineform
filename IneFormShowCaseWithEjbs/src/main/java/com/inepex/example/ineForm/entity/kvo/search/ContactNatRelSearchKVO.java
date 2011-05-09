package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactNatRelSearchKVO extends KeyValueObject {

	public static final String descriptorName = "contactNatRelSearchDescriptor";

	public ContactNatRelSearchKVO() {
		super(descriptorName);
	}

	public ContactNatRelSearchKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	
}
