package com.inepex.ineForm.client.form.widgets.kvo;

import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactNatRelKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactNatRelDescriptor";

	public static final String k_id = "id";
	public static final String k_nationality = "nationality";
	public static final String k_orderNum = "orderNum";
	public ContactNatRelKVO() {
		super(descriptorName);
	}

	public ContactNatRelKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
    public Relation getNationality() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_nationality);
    }
	
	public Long getOrderNum() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_orderNum);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setNationality(Relation nationality) {
		set(k_nationality, nationality);
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
