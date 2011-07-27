package com.inepex.example.ContactManager.entity.kvo.search;

import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class MeetingSearchKVO extends KeyValueObject {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "meetingSearchDescriptor";

	public static final String k_id = "id";
	public static final String k_meetingTimestamp = "meetingTimestamp";
	public static final String k_user = "user";
	public static final String k_company = "company";
	public static final String k_contact = "contact";
	public static final String k_meetingType = "meetingType";
	public MeetingSearchKVO() {
		super(descriptorName);
	}

	public MeetingSearchKVO(KeyValueObject other) {
		super(descriptorName);
		if (other != null)
			if (descriptorName.equals(other.getDescriptorName()))
				other.copyValuesTo(this);
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
	
	
}
