package com.inepex.example.ContactManager.entity.mapper;

import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class PhoneNumberMapper extends BaseMapper<PhoneNumber>{

	public PhoneNumber kvoToEntity(AssistedObject fromKvo, PhoneNumber to) {
		PhoneNumberKVO from = new PhoneNumberKVO(fromKvo);
		if (to == null)
			to = new PhoneNumber();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(PhoneNumberKVO.k_number)) 
			to.setNumber(from.getNumber());
		if (from.containsRelation(PhoneNumberKVO.k_type)) {
			if (from.getType() == null){
				to.setType(null);
			} else {
				to.setType(new PhoneNumberType(from.getType().getId()));
//				PhoneNumberType relatedEntity = to.getType();
//    			if (relatedEntity == null) {
//					relatedEntity = new PhoneNumberType(IFConsts.NEW_ITEM_ID);
//				}
//				new PhoneNumberTypeMapper()
//					.kvoToEntity(new PhoneNumberTypeKVO(from.getType().getKvo())
//								, relatedEntity);
//				to.setType(relatedEntity);
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public PhoneNumberKVO entityToKvo(PhoneNumber entity) {
		PhoneNumberKVO kvo = new PhoneNumberKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getNumber() != null && !"".equals(entity.getNumber())) 
			kvo.setNumber(entity.getNumber());  
		if (entity.getType() != null) 
			kvo.setType(new PhoneNumberTypeMapper().toRelation(entity.getType(), false));

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(PhoneNumber entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}