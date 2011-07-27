package com.inepex.example.ContactManager.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class EmailAddressKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "emailAddressDescriptor";

	public static final String k_id = "id";
	public static final String k_email = "email";

	public EmailAddressKVO() {
		super(descriptorName);
	}

	public EmailAddressKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
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

	public static String k_id() {
		return k_id;
	}
	
	public static String k_email() {
		return k_email;
	}
}
