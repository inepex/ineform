package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactCTypeRelKVO extends KeyValueObject {

	public static final String descriptorName = "contactCTypeRelDescriptor";

	public static final String k_id = "id";
	public static final String k_contactType = "contactType";
	public static final String k_orderNum = "orderNum";
	public ContactCTypeRelKVO() {
		super(descriptorName);
	}

	public ContactCTypeRelKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
    public Relation getContactType() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_contactType);
    }
	
	public Long getOrderNum() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_orderNum);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setContactType(Relation contactType) {
		set(k_contactType, contactType);
    }

    public void setOrderNum(Long orderNum) {
		set(k_orderNum, orderNum);
    }

	
	@Override
	public String toString() {
		String str = "";
		return str;	
	}
}
