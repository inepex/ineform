package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class Contact_ContactRoleKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contact_ContactRoleDescriptor";

	public static final String k_id = "id";
	public static final String k_role = "role";
	public static final String k_orderNum = "orderNum";
	public Contact_ContactRoleKVO() {
		super(descriptorName);
	}

	public Contact_ContactRoleKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getRole() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_role);
	}

	public Long getOrderNum() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_orderNum);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setRole(String role) {
		set(k_role, role);
    }

    public void setOrderNum(Long orderNum) {
		set(k_orderNum, orderNum);
    }

	
}
