package com.inepex.example.ineForm.entity.mapper;

import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactTypeMapper extends BaseMapper<ContactType>{

	public ContactType kvoToEntity(AssistedObject fromKvo, ContactType to) {
		ContactTypeKVO from = new ContactTypeKVO(fromKvo);
		if (to == null)
			to = new ContactType();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(ContactTypeKVO.k_typeName)) 
			to.setTypeName(from.getTypeName());
		if (from.containsString(ContactTypeKVO.k_description)) 
			to.setDescription(from.getDescription());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public ContactTypeKVO entityToKvo(ContactType entity) {
		ContactTypeKVO kvo = new ContactTypeKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getTypeName() != null && !"".equals(entity.getTypeName())) 
			kvo.setTypeName(entity.getTypeName());  
		if (entity.getDescription() != null && !"".equals(entity.getDescription())) 
			kvo.setDescription(entity.getDescription());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(ContactType entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}