package com.inepex.example.ineForm.entity.kvo.search;

import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactSearchDescriptor";

	public static final String k_firstName = "firstName";
	public static final String k_lastName = "lastName";
	public static final String k_contactTypes = "contactTypes";
	public static final String k_nationalities = "nationalities";
	public static final String k_happy = "happy";
	public static final String k_roles = "roles";
	public static final String k_states = "states";
	public ContactSearchKVO() {
		super(descriptorName);
	}

	public ContactSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
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
	
	public IneList getContactTypes() {
		if (listValues == null)
    		return null;
		return listValues.get(k_contactTypes);
    }
	public IneList getNationalities() {
		if (listValues == null)
    		return null;
		return listValues.get(k_nationalities);
    }
	public Boolean getHappy() {
		if (booleanValues == null)
    		return null;
			
		return booleanValues.get(k_happy);
	}
	
	public IneList getRoles() {
		if (listValues == null)
    		return null;
		return listValues.get(k_roles);
    }
	public IneList getStates() {
		if (listValues == null)
    		return null;
		return listValues.get(k_states);
    }
    public void setFirstName(String firstName) {
		set(k_firstName, firstName);
    }

    public void setLastName(String lastName) {
		set(k_lastName, lastName);
    }

    public void setContactTypes(IneList contactTypes) {
		set(k_contactTypes, contactTypes);
    }
	
    public void setNationalities(IneList nationalities) {
		set(k_nationalities, nationalities);
    }
	
    public void setHappy(Boolean happy) {
		set(k_happy, happy);
    }

    public void setRoles(IneList roles) {
		set(k_roles, roles);
    }
	
    public void setStates(IneList states) {
		set(k_states, states);
    }
	
	
}
