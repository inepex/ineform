package com.inepex.example.ContactManager.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.Meeting;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.example.ContactManager.entity.kvo.MeetingHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.MeetingHandlerFactory.MeetingHandler;
import com.inepex.example.ContactManager.shared.MeetingType;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class MeetingMapper extends BaseMapper<Meeting>{
	
	private final DescriptorStore descriptorStore;
	private final MeetingHandlerFactory handlerFactory;
	
	@Inject
	public MeetingMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new MeetingHandlerFactory(descriptorStore);
	}

	public Meeting kvoToEntity(AssistedObject fromKvo, Meeting to, CustomKVOObjectDesc... descs) {
		MeetingHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Meeting();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsLong(MeetingConsts.k_meetingTimestamp)) 
			to.setMeetingTimestamp(fromHandler.getMeetingTimestamp());
		if (fromHandler.containsRelation(MeetingConsts.k_user)) {
			if (fromHandler.getUser() == null){
				to.setUser(null);
			} else {
				to.setUser(new User(fromHandler.getUser().getId()));
			}
		}
		if (fromHandler.containsRelation(MeetingConsts.k_company)) {
			if (fromHandler.getCompany() == null){
				to.setCompany(null);
			} else {
				to.setCompany(new Company(fromHandler.getCompany().getId()));
			}
		}
		if (fromHandler.containsRelation(MeetingConsts.k_contact)) {
			if (fromHandler.getContact() == null){
				to.setContact(null);
			} else {
				to.setContact(new Contact(fromHandler.getContact().getId()));
			}
		}
 		if (fromHandler.containsLong(MeetingConsts.k_meetingType)) 
			to.setMeetingType(MeetingType.values()[new Long(fromHandler.getMeetingType()).intValue()]);
		if (fromHandler.containsString(MeetingConsts.k_description)) 
			to.setDescription(fromHandler.getDescription());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(Meeting entity) {
		MeetingHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getMeetingTimestamp() != null) 
			handler.setMeetingTimestamp(entity.getMeetingTimestamp());
		if (entity.getUser() != null) 
			handler.setUser(new UserMapper(descriptorStore).toRelation(entity.getUser(), false));
		if (entity.getCompany() != null) 
			handler.setCompany(new CompanyMapper(descriptorStore).toRelation(entity.getCompany(), false));
		if (entity.getContact() != null) 
			handler.setContact(new ContactMapper(descriptorStore).toRelation(entity.getContact(), false));
		if (entity.getMeetingType() != null){
			handler.setMeetingType(new Long(entity.getMeetingType().ordinal()));
		}
		if (entity.getDescription() != null && !"".equals(entity.getDescription())) 
			handler.setDescription(entity.getDescription());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(Meeting entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}