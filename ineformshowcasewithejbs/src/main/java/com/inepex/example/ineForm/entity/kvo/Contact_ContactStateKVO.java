package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class Contact_ContactStateKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contact_ContactStateDescriptor";

	public static final String k_id = "id";
	public static final String k_state = "state";
	public static final String k_orderNum = "orderNum";
	public Contact_ContactStateKVO() {
		super(descriptorName);
	}

	public Contact_ContactStateKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public Long getState() {
		if (longValues == null)
    		return null;

		return longValues.get(k_state);
	}
	
	public Long getOrderNum() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_orderNum);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setState(Long state) {
		set(k_state, state);
    }
	
    public void setOrderNum(Long orderNum) {
		set(k_orderNum, orderNum);
    }

	
}
