package com.inepex.example.ineForm.dto.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactDtoSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactDtoSearchDescriptor";

	public ContactDtoSearchKVO() {
		super(descriptorName);
	}

	public ContactDtoSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	
}
