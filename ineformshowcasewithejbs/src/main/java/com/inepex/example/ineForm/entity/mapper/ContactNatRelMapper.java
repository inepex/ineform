package com.inepex.example.ineForm.entity.mapper;

import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactNatRelMapper extends BaseMapper<ContactNatRel>{

	public ContactNatRel kvoToEntity(AssistedObject fromKvo, ContactNatRel to) {
		ContactNatRelKVO from = new ContactNatRelKVO(fromKvo);
		if (to == null)
			to = new ContactNatRel();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsRelation(ContactNatRelKVO.k_nationality)) {
			if (from.getNationality() == null){
				to.setNationality(null);
			} else {
				to.setNationality(new Nationality(from.getNationality().getId()));
			}
		}
		if (from.containsLong(ContactNatRelKVO.k_orderNum)) 
			to.setOrderNum(from.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public ContactNatRelKVO entityToKvo(ContactNatRel entity) {
		ContactNatRelKVO kvo = new ContactNatRelKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getNationality() != null) 
			kvo.setNationality(new NationalityMapper().toRelation(entity.getNationality(), false));
		if (entity.getOrderNum() != null) 
			kvo.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(ContactNatRel entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}