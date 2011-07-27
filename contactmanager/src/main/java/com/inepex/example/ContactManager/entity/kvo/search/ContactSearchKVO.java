package com.inepex.example.ContactManager.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactSearchDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public static final String k_company = "company";
	public ContactSearchKVO() {
		super(descriptorName);
	}

	public ContactSearchKVO(KeyValueObject other) {
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
	
    public Relation getCompany() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_company);
    }
	
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setName(String name) {
		set(k_name, name);
    }

    public void setCompany(Relation company) {
		set(k_company, company);
    }

	
}
