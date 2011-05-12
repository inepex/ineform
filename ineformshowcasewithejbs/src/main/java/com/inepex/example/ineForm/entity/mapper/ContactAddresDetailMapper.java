
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactAddresDetailMapper {

	public ContactAddresDetail kvoToEntity(ContactAddresDetailKVO from, ContactAddresDetail to) {
		if (to == null)
			to = new ContactAddresDetail();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(ContactAddresDetailKVO.k_city)) 
			to.setCity(from.getCity());
		if (from.containsString(ContactAddresDetailKVO.k_country)) 
			to.setCountry(from.getCountry());
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
	
	public List<Relation> toRelationList(List<ContactAddresDetail> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<ContactAddresDetail> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (ContactAddresDetail entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<ContactAddresDetail> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (ContactAddresDetail o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
