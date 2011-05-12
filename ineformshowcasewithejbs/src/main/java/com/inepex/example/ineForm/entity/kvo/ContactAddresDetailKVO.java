package com.inepex.example.ineForm.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ContactAddresDetailKVO extends KeyValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactAddresDetailDescriptor";

	public static final String k_id = "id";
	public static final String k_city = "city";
	public static final String k_country = "country";
	public ContactAddresDetailKVO() {
		super(descriptorName);
	}

	public ContactAddresDetailKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public String getCity() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_city);
	}

	public String getCountry() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_country);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setCity(String city) {
		set(k_city, city);
    }

    public void setCountry(String country) {
		set(k_country, country);
    }

	
	@Override
	public String toString() {
		String str = "";
		if (getCity() != null)
			str+=getCity().toString() + " ";
		return str;	
	}
}
