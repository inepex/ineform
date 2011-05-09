package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactDescriptor";

	public static final String k_id = "id";
	public static final String k_firstName = "firstName";
	public static final String k_lastName = "lastName";
	public static final String k_address = "address";
	public static final String k_createDate = "createDate";
	public static final String k_numOfAccess = "numOfAccess";
	public static final String k_contactTypes = "contactTypes";
	public static final String k_profilePhoto = "profilePhoto";
	public static final String k_nationalities = "nationalities";
	public static final String k_addressDetail = "addressDetail";
	public static final String k_happy = "happy";
	public static final String k_roles = "roles";
	public static final String k_states = "states";
	public ContactKVO() {
		super(descriptorName);
	}

	public ContactKVO(AssistedObject other) {
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

	public String getAddress() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_address);
	}

	public Long getCreateDate() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_createDate);
	}

	public Long getNumOfAccess() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_numOfAccess);
	}

	public IneList getContactTypes() {
		if (listValues == null)
    		return null;
		return listValues.get(k_contactTypes);
    }
	public String getProfilePhoto() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_profilePhoto);
	}

	public IneList getNationalities() {
		if (listValues == null)
    		return null;
		return listValues.get(k_nationalities);
    }
    public Relation getAddressDetail() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_addressDetail);
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
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setFirstName(String firstName) {
		set(k_firstName, firstName);
    }

    public void setLastName(String lastName) {
		set(k_lastName, lastName);
    }

    public void setAddress(String address) {
		set(k_address, address);
    }

    public void setCreateDate(Long createDate) {
		set(k_createDate, createDate);
    }

    public void setNumOfAccess(Long numOfAccess) {
		set(k_numOfAccess, numOfAccess);
    }

    public void setContactTypes(IneList contactTypes) {
		set(k_contactTypes, contactTypes);
    }
	
    public void setProfilePhoto(String profilePhoto) {
		set(k_profilePhoto, profilePhoto);
    }

    public void setNationalities(IneList nationalities) {
		set(k_nationalities, nationalities);
    }
	
    public void setAddressDetail(Relation addressDetail) {
		set(k_addressDetail, addressDetail);
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
