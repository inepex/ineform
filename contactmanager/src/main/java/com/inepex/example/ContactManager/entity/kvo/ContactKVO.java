package com.inepex.example.ContactManager.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactDescriptor";

	public static final String k_id = "id";
	public static final String k_name = "name";
	public static final String k_phone = "phone";
	public static final String k_email = "email";
	public static final String k_company = "company";

	public ContactKVO() {
		super(descriptorName);
	}

	public ContactKVO(AssistedObject other) {
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

	public IneList getPhone() {
		if (listValues == null)
    		return null;
		return listValues.get(k_phone);
    }
	public IneList getEmail() {
		if (listValues == null)
    		return null;
		return listValues.get(k_email);
    }
    public Relation getCompany() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_company);
    }
	
    public void setId(Long id) {
		set(k_id, id);
    }

    public void setName(String name) {
		set(k_name, name);
    }

    public void setPhone(IneList phone) {
		set(k_phone, phone);
    }
	
    public void setEmail(IneList email) {
		set(k_email, email);
    }
	
    public void setCompany(Relation company) {
		set(k_company, company);
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
	
	public static String k_company() {
		return k_company;
	}
	

}
