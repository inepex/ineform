package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class PhoneNumberTypeMapper {

	public PhoneNumberType kvoToEntity(PhoneNumberTypeKVO from, PhoneNumberType to) {
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
	
	public List<Relation> toRelationList(List<PhoneNumberType> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<PhoneNumberType> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (PhoneNumberType entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<PhoneNumberType> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (PhoneNumberType o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
