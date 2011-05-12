package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class NationalityKVO extends KeyValueObject {

	public static final String descriptorName = "nationalityDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public static final String k_description = "description";
	public NationalityKVO() {
		super(descriptorName);
	}

	public NationalityKVO(AssistedObject other) {
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

	public String getDescription() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_description);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setName(String name) {
		set(k_name, name);
    }

    public void setDescription(String description) {
		set(k_description, description);
    }

	
	@Override
	public String toString() {
		String str = "";
		if (getName() != null)
			str+=getName().toString() + " ";
		return str;	
	}
}
