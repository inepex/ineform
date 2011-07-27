package com.inepex.example.ContactManager.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class PhoneNumberSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "phoneNumberSearchDescriptor";

	public static final String k_id = "id";
	public static final String k_number = "number";
	public static final String k_type = "type";
	public PhoneNumberSearchKVO() {
		super(descriptorName);
	}

	public PhoneNumberSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getNumber() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_number);
	}
	
    public Relation getType() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_type);
    }
	
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setNumber(String number) {
		set(k_number, number);
    }

    public void setType(Relation type) {
		set(k_type, type);
    }

	
}
