package com.inepex.example.ineForm.entity.mapper;

import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactAddresDetailMapper extends BaseMapper<ContactAddresDetail>{

	public ContactAddresDetail kvoToEntity(AssistedObject fromKvo, ContactAddresDetail to) {
		ContactAddresDetailKVO from = new ContactAddresDetailKVO(fromKvo);
		if (to == null)
			to = new ContactAddresDetail();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(ContactAddresDetailKVO.k_city)) 
			to.setCity(from.getCity());
		if (from.containsString(ContactAddresDetailKVO.k_country)) 
			to.setCountry(from.getCountry());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public ContactAddresDetailKVO entityToKvo(ContactAddresDetail entity) {
		ContactAddresDetailKVO kvo = new ContactAddresDetailKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getCity() != null && !"".equals(entity.getCity())) 
			kvo.setCity(entity.getCity());  
		if (entity.getCountry() != null && !"".equals(entity.getCountry())) 
			kvo.setCountry(entity.getCountry());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(ContactAddresDetail entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}