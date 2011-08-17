package com.inepex.example.ContactManager.entity.mapper;

import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class PhoneNumberTypeMapper extends BaseMapper<PhoneNumberType>{

	public PhoneNumberType kvoToEntity(AssistedObject fromKvo, PhoneNumberType to) {
		PhoneNumberTypeKVO from = new PhoneNumberTypeKVO(fromKvo);
		if (to == null)
			to = new PhoneNumberType();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(PhoneNumberTypeKVO.k_name)) 
			to.setName(from.getName());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public PhoneNumberTypeKVO entityToKvo(PhoneNumberType entity) {
		PhoneNumberTypeKVO kvo = new PhoneNumberTypeKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			kvo.setName(entity.getName());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(PhoneNumberType entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}