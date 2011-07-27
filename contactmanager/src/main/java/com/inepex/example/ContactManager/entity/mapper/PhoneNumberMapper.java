package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class PhoneNumberMapper {

	public PhoneNumber kvoToEntity(PhoneNumberKVO from, PhoneNumber to) {
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
	
	public List<Relation> toRelationList(List<PhoneNumber> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<PhoneNumber> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (PhoneNumber entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<PhoneNumber> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (PhoneNumber o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
