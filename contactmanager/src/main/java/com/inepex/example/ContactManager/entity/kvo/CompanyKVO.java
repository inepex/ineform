package com.inepex.example.ContactManager.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class CompanyKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "companyDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public static final String k_phone = "phone";
	public static final String k_email = "email";
	public static final String k_webPage = "webPage";
	public static final String k_contacts = "contacts";

	public CompanyKVO() {
		super(descriptorName);
	}

	public CompanyKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
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

	public IneList getContacts() {
		if (listValues == null)
    		return null;
		return listValues.get(k_contacts);
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

    public void setContacts(IneList contacts) {
		set(k_contacts, contacts);
    }
	

	public static String k_id() {
		return k_id;
	}
	
	public static String k_name() {
		return k_name;
	}
	
	public static String k_phone() {
		return k_phone;
	}
	
	public static String k_email() {
		return k_email;
	}
	
	public static String k_webPage() {
		return k_webPage;
	}
	
	public static String k_contacts() {
		return k_contacts;
	}
	

}
