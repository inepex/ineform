
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class Contact_ContactRoleMapper {

	public Contact_ContactRole kvoToEntity(Contact_ContactRoleKVO from, Contact_ContactRole to) {
		if (to == null)
			to = new Contact_ContactRole();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(Contact_ContactRoleKVO.k_role)) 
			to.setRole(from.getRole());
		if (from.containsLong(Contact_ContactRoleKVO.k_orderNum)) 
			to.setOrderNum(from.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public Contact_ContactRoleKVO entityToKvo(Contact_ContactRole entity) {
		Contact_ContactRoleKVO kvo = new Contact_ContactRoleKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getRole() != null && !"".equals(entity.getRole())) 
			kvo.setRole(entity.getRole());  
		if (entity.getOrderNum() != null) 
			kvo.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(Contact_ContactRole entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<Contact_ContactRole> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<Contact_ContactRole> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (Contact_ContactRole entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<Contact_ContactRole> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (Contact_ContactRole o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
