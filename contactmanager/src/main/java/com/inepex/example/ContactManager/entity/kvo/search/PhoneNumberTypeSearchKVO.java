package com.inepex.example.ContactManager.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;

public class PhoneNumberTypeSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "phoneNumberTypeSearchDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public PhoneNumberTypeSearchKVO() {
		super(descriptorName);
	}

	public PhoneNumberTypeSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getName() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_name);
	}
	
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setName(String name) {
		set(k_name, name);
    }

	
}
