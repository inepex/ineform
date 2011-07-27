package com.inepex.example.ContactManager.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;

public class CompanySearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "companySearchDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public static final String k_phone = "phone";
	public static final String k_email = "email";
	public static final String k_webPage = "webPage";
	public CompanySearchKVO() {
		super(descriptorName);
	}

	public CompanySearchKVO(KeyValueObject other) {
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
	
	public String getPhone() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_phone);
	}
	
	public String getEmail() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_email);
	}
	
	public String getWebPage() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_webPage);
	}
	
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setName(String name) {
		set(k_name, name);
    }

    public void setPhone(String phone) {
		set(k_phone, phone);
    }

    public void setEmail(String email) {
		set(k_email, email);
    }

    public void setWebPage(String webPage) {
		set(k_webPage, webPage);
    }

	
}
