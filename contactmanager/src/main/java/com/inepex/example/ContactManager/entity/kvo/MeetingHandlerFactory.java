package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class MeetingHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new MeetingHandlerFactory(decStore)'
	 */
	@Inject
	public MeetingHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public MeetingHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(MeetingConsts.descriptorName);
		return new MeetingHandler(assistedObject, descriptorStore);
	}
	
	public MeetingHandler createHandler(AssistedObject assistedObject) {
		if(!MeetingConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+MeetingConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new MeetingHandler(assistedObject, descriptorStore);
	}
	
	public MeetingSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(MeetingConsts.searchDescriptor);
		return new MeetingSearchHandler(assistedObject, descriptorStore);
	}
	
	public MeetingSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!MeetingConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+MeetingConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new MeetingSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class MeetingHandler extends AssistedObjectHandler {
	
		private MeetingHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Long getMeetingTimestamp() {
        	return getLong(MeetingConsts.k_meetingTimestamp);
        }
        
        public void setMeetingTimestamp(Long meetingTimestamp) {
    		set(MeetingConsts.k_meetingTimestamp, meetingTimestamp);
        }
			
        public Relation getUser() {
    		return getRelation(MeetingConsts.k_user);
        }	
        public void setUser(Relation user) {
    		set(MeetingConsts.k_user, user);
        }
			
        public Relation getCompany() {
    		return getRelation(MeetingConsts.k_company);
        }	
        public void setCompany(Relation company) {
    		set(MeetingConsts.k_company, company);
        }
			
        public Relation getContact() {
    		return getRelation(MeetingConsts.k_contact);
        }	
        public void setContact(Relation contact) {
    		set(MeetingConsts.k_contact, contact);
        }
			
        public Long getMeetingType() {
    		return getLong(MeetingConsts.k_meetingType);
    	}
        	
        public void setMeetingType(Long meetingType) {
    		set(MeetingConsts.k_meetingType, meetingType);
        }	
			
        public String getDescription() {
        	return getString(MeetingConsts.k_description);
        }
        
        public void setDescription(String description) {
    		set(MeetingConsts.k_description, description);
        }
	}

	public static class MeetingSearchHandler extends AssistedObjectHandler {
	
		private MeetingSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public Long getMeetingTimestamp() {
        	return getLong(MeetingConsts.s_meetingTimestamp);
        }
        
        public void setMeetingTimestamp(Long meetingTimestamp) {
    		set(MeetingConsts.s_meetingTimestamp, meetingTimestamp);
        }
        public Relation getUser() {
    		return getRelation(MeetingConsts.s_user);
        }	
        public void setUser(Relation user) {
    		set(MeetingConsts.s_user, user);
        }
        public Relation getCompany() {
    		return getRelation(MeetingConsts.s_company);
        }	
        public void setCompany(Relation company) {
    		set(MeetingConsts.s_company, company);
        }
        public Relation getContact() {
    		return getRelation(MeetingConsts.s_contact);
        }	
        public void setContact(Relation contact) {
    		set(MeetingConsts.s_contact, contact);
        }
        public Long getMeetingType() {
    		return getLong(MeetingConsts.s_meetingType);
    	}
        	
        public void setMeetingType(Long meetingType) {
    		set(MeetingConsts.s_meetingType, meetingType);
        }	
	}
}