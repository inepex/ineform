package com.inepex.example.ContactManager.entity.kvo;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class MeetingKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "meetingDescriptor";

	public static final String k_id = "id";
	public static final String k_meetingTimestamp = "meetingTimestamp";
	public static final String k_user = "user";
	public static final String k_company = "company";
	public static final String k_contact = "contact";
	public static final String k_meetingType = "meetingType";
	public static final String k_description = "description";

	public MeetingKVO() {
		super(descriptorName);
	}

	public MeetingKVO(AssistedObject other) {
		super(descriptorName);
		if (other != null)
			((KeyValueObject)other).shallowCopyTo(this);
	}
	
	public Long getId() {
		return super.getId();
	}
	
	public Long getMeetingTimestamp() {
		if (longValues == null)
    		return null;
			
		return longValues.get(k_meetingTimestamp);
	}

    public Relation getUser() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_user);
    }
	
    public Relation getCompany() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_company);
    }
	
    public Relation getContact() {
		if (relationValues == null)
    		return null;
		return relationValues.get(k_contact);
    }
	
	public Long getMeetingType() {
		if (longValues == null)
    		return null;

		return longValues.get(k_meetingType);
	}
	
	public String getDescription() {
		if (stringValues == null)
    		return null;
			
		return stringValues.get(k_description);
	}

    public void setId(Long id) {
		set(k_id, id);
    }

    public void setMeetingTimestamp(Long meetingTimestamp) {
		set(k_meetingTimestamp, meetingTimestamp);
    }

    public void setUser(Relation user) {
		set(k_user, user);
    }

    public void setCompany(Relation company) {
		set(k_company, company);
    }

    public void setContact(Relation contact) {
		set(k_contact, contact);
    }

    public void setMeetingType(Long meetingType) {
		set(k_meetingType, meetingType);
    }
	
    public void setDescription(String description) {
		set(k_description, description);
    }


	public static String k_id() {
		return k_id;
	}
	
	public static String k_meetingTimestamp() {
		return k_meetingTimestamp;
	}
	
	public static String k_user() {
		return k_user;
	}
	
	public static String k_company() {
		return k_company;
	}
	
	public static String k_contact() {
		return k_contact;
	}
	
	public static String k_meetingType() {
		return k_meetingType;
	}
	
	public static String k_description() {
		return k_description;
	}
	

}
