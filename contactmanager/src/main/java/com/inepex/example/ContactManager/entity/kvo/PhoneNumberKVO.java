package com.inepex.example.ContactManager.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class PhoneNumberKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "phoneNumberDescriptor";

	public static final String k_id = "id";
	public static final String k_number = "number";
	public static final String k_type = "type";

	public PhoneNumberKVO() {
		super(descriptorName);
	}

	public PhoneNumberKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
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

	public static String k_id() {
		return k_id;
	}
	
	public static String k_number() {
		return k_number;
	}
	
	public static String k_type() {
		return k_type;
	}
}
