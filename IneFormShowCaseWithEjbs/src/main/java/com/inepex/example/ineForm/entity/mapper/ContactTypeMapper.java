
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactTypeMapper {

	public ContactType kvoToEntity(ContactTypeKVO from, ContactType to) {
		if (to == null)
			to = new ContactType();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(ContactTypeKVO.k_typeName)) 
			to.setTypeName(from.getTypeName());
		if (from.containsString(ContactTypeKVO.k_description)) 
			to.setDescription(from.getDescription());
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
	
	public List<Relation> toRelationList(List<ContactType> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<ContactType> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (ContactType entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<ContactType> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (ContactType o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
