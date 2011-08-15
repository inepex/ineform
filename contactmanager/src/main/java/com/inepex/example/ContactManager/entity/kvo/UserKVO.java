package com.inepex.example.ContactManager.entity.kvo;


import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class UserKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "userDescriptor";

	public static final String k_id = "id";
	public static final String k_firstName = "firstName";
	public static final String k_lastName = "lastName";
	public static final String k_email = "email";

	public UserKVO() {
		super(descriptorName);
	}

	public UserKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getFirstName() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_firstName);
	}

	public String getLastName() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_lastName);
	}

	public String getEmail() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_email);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setFirstName(String firstName) {
		set(k_firstName, firstName);
    }

    public void setLastName(String lastName) {
		set(k_lastName, lastName);
    }

    public void setEmail(String email) {
		set(k_email, email);
    }


	public static String k_id() {
		return k_id;
	}
	
	public static String k_firstName() {
		return k_firstName;
	}
	
	public static String k_lastName() {
		return k_lastName;
	}
	
	public static String k_email() {
		return k_email;
	}
	

}