package com.inepex.example.ContactManager.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class PhoneNumberTypeKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "phoneNumberTypeDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";

	public PhoneNumberTypeKVO() {
		super(descriptorName);
	}

	public PhoneNumberTypeKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
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


	public static String k_id() {
		return k_id;
	}
	
	public static String k_name() {
		return k_name;
	}
	

}
