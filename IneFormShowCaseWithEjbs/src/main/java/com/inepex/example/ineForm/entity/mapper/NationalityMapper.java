
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.kvo.NationalityKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class NationalityMapper {

	public Nationality kvoToEntity(NationalityKVO from, Nationality to) {
		if (to == null)
			to = new Nationality();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(NationalityKVO.k_name)) 
			to.setName(from.getName());
		if (from.containsString(NationalityKVO.k_description)) 
			to.setDescription(from.getDescription());
		return to;
	}
	
	public NationalityKVO entityToKvo(Nationality entity) {
		NationalityKVO kvo = new NationalityKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			kvo.setName(entity.getName());  
		if (entity.getDescription() != null && !"".equals(entity.getDescription())) 
			kvo.setDescription(entity.getDescription());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(Nationality entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<Nationality> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<Nationality> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (Nationality entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<Nationality> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (Nationality o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
