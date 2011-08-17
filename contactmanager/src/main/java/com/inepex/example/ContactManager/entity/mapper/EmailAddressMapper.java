package com.inepex.example.ContactManager.entity.mapper;

import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class EmailAddressMapper extends BaseMapper<EmailAddress>{

	public EmailAddress kvoToEntity(AssistedObject fromKvo, EmailAddress to) {
		EmailAddressKVO from = new EmailAddressKVO(fromKvo);
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
	
}