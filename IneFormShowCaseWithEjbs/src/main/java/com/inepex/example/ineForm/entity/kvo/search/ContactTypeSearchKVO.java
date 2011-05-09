package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactTypeSearchKVO extends KeyValueObject {

	public static final String descriptorName = "contactTypeSearchDescriptor";

	public static final String k_typeName = "typeName";
	public ContactTypeSearchKVO() {
		super(descriptorName);
	}

	public ContactTypeSearchKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public String getTypeName() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_typeName);
	}
	
    public void setTypeName(String typeName) {
		set(k_typeName, typeName);
    }

	
}
