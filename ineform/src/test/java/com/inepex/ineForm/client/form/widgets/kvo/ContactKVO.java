package com.inepex.ineForm.client.form.widgets.kvo;

import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;

public class ContactKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactDescriptor";

	public static final String k_id = "id";
	public static final String k_nationalities = "nationalities";
	public ContactKVO() {
		super(descriptorName);
	}

	public ContactKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
//				other.copyValuesTo(this);
				//TODO
				//TODO
				//TODO
				;
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public IneList getNationalities() {
		if (listValues == null)
    		return null;
		return listValues.get(k_nationalities);
    }
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setNationalities(IneList nationalities) {
		set(k_nationalities, nationalities);
    }
	
	
	@Override
	public String toString() {
		String str = "";
		return str;	
	}
}
