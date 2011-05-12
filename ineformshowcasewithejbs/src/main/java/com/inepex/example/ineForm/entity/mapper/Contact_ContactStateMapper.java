
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateKVO;
import com.inepex.example.ineForm.enums.ContactState;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class Contact_ContactStateMapper {

	public Contact_ContactState kvoToEntity(Contact_ContactStateKVO from, Contact_ContactState to) {
		if (to == null)
			to = new Contact_ContactState();
		if (!from.isNew()) 
			to.setId(from.getId());
 		if (from.containsLong(Contact_ContactStateKVO.k_state)) 
			to.setState(ContactState.values()[new Long(from.getState()).intValue()]);
		if (from.containsLong(Contact_ContactStateKVO.k_orderNum)) 
			to.setOrderNum(from.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public Contact_ContactStateKVO entityToKvo(Contact_ContactState entity) {
		Contact_ContactStateKVO kvo = new Contact_ContactStateKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getState() != null){
			kvo.setState(new Long(entity.getState().ordinal()));
		}
		if (entity.getOrderNum() != null) 
			kvo.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(Contact_ContactState entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<Contact_ContactState> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<Contact_ContactState> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (Contact_ContactState entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<Contact_ContactState> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (Contact_ContactState o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
