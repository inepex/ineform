package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class NationalitySearchKVO extends KeyValueObject {

	public static final String descriptorName = "nationalitySearchDescriptor";

	public NationalitySearchKVO() {
		super(descriptorName);
	}

	public NationalitySearchKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	
}
