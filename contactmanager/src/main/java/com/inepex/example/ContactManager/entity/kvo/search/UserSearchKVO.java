package com.inepex.example.ContactManager.entity.kvo.search;


import com.inepex.ineom.shared.kvo.KeyValueObject;

public class UserSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "userSearchDescriptor";

	public static final String k_id = "id";
	public static final String k_firstName = "firstName";
	public static final String k_lastName = "lastName";
	public static final String k_email = "email";
	public UserSearchKVO() {
		super(descriptorName);
	}

	public UserSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
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

	
}