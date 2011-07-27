package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class EmailAddressMapper {

	public EmailAddress kvoToEntity(EmailAddressKVO from, EmailAddress to) {
		if (to == null)
			to = new EmailAddress();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(EmailAddressKVO.k_email)) 
			to.setEmail(from.getEmail());
		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public EmailAddressKVO entityToKvo(EmailAddress entity) {
		EmailAddressKVO kvo = new EmailAddressKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			kvo.setEmail(entity.getEmail());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(EmailAddress entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<EmailAddress> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<EmailAddress> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (EmailAddress entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<EmailAddress> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (EmailAddress o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
