package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;

public class Contact_ContactStateSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contact_ContactStateSearchDescriptor";

	public Contact_ContactStateSearchKVO() {
		super(descriptorName);
	}

	public Contact_ContactStateSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	
}
