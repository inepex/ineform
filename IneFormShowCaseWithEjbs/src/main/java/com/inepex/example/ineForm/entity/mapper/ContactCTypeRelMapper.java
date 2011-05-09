
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactCTypeRelMapper {

	public ContactCTypeRel kvoToEntity(ContactCTypeRelKVO from, ContactCTypeRel to) {
		if (to == null)
			to = new ContactCTypeRel();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsRelation(ContactCTypeRelKVO.k_contactType)) {
			if (from.getContactType() == null){
				to.setContactType(null);
			} else {
				to.setContactType(new ContactType(from.getContactType().getId()));
			}
		}
		if (from.containsLong(ContactCTypeRelKVO.k_orderNum)) 
			to.setOrderNum(from.getOrderNum());
		return to;
	}
	
	public ContactCTypeRelKVO entityToKvo(ContactCTypeRel entity) {
		ContactCTypeRelKVO kvo = new ContactCTypeRelKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getContactType() != null) 
			kvo.setContactType(new ContactTypeMapper().toRelation(entity.getContactType(), true));
		if (entity.getOrderNum() != null) 
			kvo.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(ContactCTypeRel entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<ContactCTypeRel> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<ContactCTypeRel> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (ContactCTypeRel entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<ContactCTypeRel> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (ContactCTypeRel o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
