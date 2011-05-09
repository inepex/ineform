package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactTypeKVO extends KeyValueObject {

	public static final String descriptorName = "contactTypeDescriptor";

	public static final String k_id = "id";
	public static final String k_typeName = "typeName";
	public static final String k_description = "description";
	public ContactTypeKVO() {
		super(descriptorName);
	}

	public ContactTypeKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getTypeName() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_typeName);
	}

	public String getDescription() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_description);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setTypeName(String typeName) {
		set(k_typeName, typeName);
    }

    public void setDescription(String description) {
		set(k_description, description);
    }

	
	@Override
	public String toString() {
		String str = "";
		return str;	
	}
}
