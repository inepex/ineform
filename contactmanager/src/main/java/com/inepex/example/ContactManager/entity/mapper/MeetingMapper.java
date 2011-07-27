package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.Meeting;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.kvo.MeetingKVO;
import com.inepex.example.ContactManager.shared.MeetingType;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class MeetingMapper {

	public Meeting kvoToEntity(MeetingKVO from, Meeting to) {
		if (to == null)
			to = new Meeting();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsLong(MeetingKVO.k_meetingTimestamp)) 
			to.setMeetingTimestamp(from.getMeetingTimestamp());
		if (from.containsRelation(MeetingKVO.k_user)) {
			if (from.getUser() == null){
				to.setUser(null);
			} else {
				to.setUser(new User(from.getUser().getId()));
			}
		}
		if (from.containsRelation(MeetingKVO.k_company)) {
			if (from.getCompany() == null){
				to.setCompany(null);
			} else {
				to.setCompany(new Company(from.getCompany().getId()));
			}
		}
		if (from.containsRelation(MeetingKVO.k_contact)) {
			if (from.getContact() == null){
				to.setContact(null);
			} else {
				to.setContact(new Contact(from.getContact().getId()));
			}
		}
 		if (from.containsLong(MeetingKVO.k_meetingType)) 
			to.setMeetingType(MeetingType.values()[new Long(from.getMeetingType()).intValue()]);
		if (from.containsString(MeetingKVO.k_description)) 
			to.setDescription(from.getDescription());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public MeetingKVO entityToKvo(Meeting entity) {
		MeetingKVO kvo = new MeetingKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getMeetingTimestamp() != null) 
			kvo.setMeetingTimestamp(entity.getMeetingTimestamp());
		if (entity.getUser() != null) 
			kvo.setUser(new UserMapper().toRelation(entity.getUser(), false));
		if (entity.getCompany() != null) 
			kvo.setCompany(new CompanyMapper().toRelation(entity.getCompany(), false));
		if (entity.getContact() != null) 
			kvo.setContact(new ContactMapper().toRelation(entity.getContact(), false));
		if (entity.getMeetingType() != null){
			kvo.setMeetingType(new Long(entity.getMeetingType().ordinal()));
		}
		if (entity.getDescription() != null && !"".equals(entity.getDescription())) 
			kvo.setDescription(entity.getDescription());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(Meeting entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<Meeting> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<Meeting> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (Meeting entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<Meeting> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (Meeting o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
