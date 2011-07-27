package com.inepex.example.ContactManager.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;

public class EmailAddressSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "emailAddressSearchDescriptor";

	public static final String k_id = "id";
	public static final String k_email = "email";
	public EmailAddressSearchKVO() {
		super(descriptorName);
	}

	public EmailAddressSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getEmail() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_email);
	}
	
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setEmail(String email) {
		set(k_email, email);
    }

	
}
