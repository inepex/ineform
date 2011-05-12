package com.inepex.ineForm.client.form.widgets.kvo;

import com.inepex.ineom.shared.kvo.KeyValueObject;

public class NationalityKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "nationalityDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public NationalityKVO() {
		super(descriptorName);
	}

	public NationalityKVO(KeyValueObject other) {
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

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setName(String name) {
		set(k_name, name);
    }

	
	@Override
	public String toString() {
		String str = "";
		if (getName() != null)
			str+=getName().toString() + " ";
		return str;	
	}
}
