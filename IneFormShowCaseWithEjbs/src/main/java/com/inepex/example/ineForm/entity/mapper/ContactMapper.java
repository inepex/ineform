
package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailKVO;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelKVO;
import com.inepex.example.ineForm.entity.kvo.ContactKVO;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelKVO;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleKVO;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateKVO;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactMapper {

	public Contact kvoToEntity(ContactKVO from, Contact to) {
		if (to == null)
			to = new Contact();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(ContactKVO.k_firstName)) 
			to.setFirstName(from.getFirstName());
		if (from.containsString(ContactKVO.k_lastName)) 
			to.setLastName(from.getLastName());
		if (from.containsString(ContactKVO.k_address)) 
			to.setAddress(from.getAddress());
		if (from.containsLong(ContactKVO.k_createDate)) 
			to.setCreateDate(from.getCreateDate());
		if (from.containsLong(ContactKVO.k_numOfAccess)) 
			to.setNumOfAccess(from.getNumOfAccess());
		if (from.containsList(ContactKVO.k_contactTypes)) {
			if (to.getContactTypes() == null)
				to.setContactTypes(new ArrayList<ContactCTypeRel>());

    		Map<Long,ContactCTypeRel> origItems = new HashMap<Long, ContactCTypeRel>();
			for (ContactCTypeRel item : to.getContactTypes()) {
				origItems.put(item.getId(), item);
			}
			
			ContactCTypeRelMapper mapper = new ContactCTypeRelMapper();
			for (Relation rel : from.getContactTypes().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					ContactCTypeRel entity = new ContactCTypeRel(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new ContactCTypeRelKVO(rel.getKvo()), entity);
					entity.setContact(to);
					to.getContactTypes().add(entity);
				} else {
					ContactCTypeRel origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getContactTypes().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new ContactCTypeRelKVO(rel.getKvo())
										 , origItem);
					}
				}
			}
		}
		if (from.containsString(ContactKVO.k_profilePhoto)) 
			to.setProfilePhoto(from.getProfilePhoto());
		if (from.containsList(ContactKVO.k_nationalities)) {
			if (to.getNationalities() == null)
				to.setNationalities(new ArrayList<ContactNatRel>());

    		Map<Long,ContactNatRel> origItems = new HashMap<Long, ContactNatRel>();
			for (ContactNatRel item : to.getNationalities()) {
				origItems.put(item.getId(), item);
			}
			
			ContactNatRelMapper mapper = new ContactNatRelMapper();
			for (Relation rel : from.getNationalities().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					ContactNatRel entity = new ContactNatRel(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new ContactNatRelKVO(rel.getKvo()), entity);
					entity.setContact(to);
					to.getNationalities().add(entity);
				} else {
					ContactNatRel origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getNationalities().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new ContactNatRelKVO(rel.getKvo())
										 , origItem);
					}
				}
			}
		}
		if (from.containsRelation(ContactKVO.k_addressDetail)) {
			if (from.getAddressDetail() == null){
				to.setAddressDetail(null);
			} else {
				ContactAddresDetail relatedEntity = to.getAddressDetail();
    			if (relatedEntity == null) {
					relatedEntity = new ContactAddresDetail(IFConsts.NEW_ITEM_ID);
				}
				new ContactAddresDetailMapper()
					.kvoToEntity(new ContactAddresDetailKVO(from.getAddressDetail().getKvo())
								, relatedEntity);
				to.setAddressDetail(relatedEntity);
			}
		}
		if (from.containsBoolean(ContactKVO.k_happy)) 
			to.setHappy(from.getHappy());
		if (from.containsList(ContactKVO.k_roles)) {
			if (to.getRoles() == null)
				to.setRoles(new ArrayList<Contact_ContactRole>());

    		Map<Long,Contact_ContactRole> origItems = new HashMap<Long, Contact_ContactRole>();
			for (Contact_ContactRole item : to.getRoles()) {
				origItems.put(item.getId(), item);
			}
			
			Contact_ContactRoleMapper mapper = new Contact_ContactRoleMapper();
			for (Relation rel : from.getRoles().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					Contact_ContactRole entity = new Contact_ContactRole(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new Contact_ContactRoleKVO(rel.getKvo()), entity);
					entity.setContact(to);
					to.getRoles().add(entity);
				} else {
					Contact_ContactRole origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getRoles().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new Contact_ContactRoleKVO(rel.getKvo())
										 , origItem);
					}
				}
			}
		}
		if (from.containsList(ContactKVO.k_states)) {
			if (to.getStates() == null)
				to.setStates(new ArrayList<Contact_ContactState>());

    		Map<Long,Contact_ContactState> origItems = new HashMap<Long, Contact_ContactState>();
			for (Contact_ContactState item : to.getStates()) {
				origItems.put(item.getId(), item);
			}
			
			Contact_ContactStateMapper mapper = new Contact_ContactStateMapper();
			for (Relation rel : from.getStates().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					Contact_ContactState entity = new Contact_ContactState(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new Contact_ContactStateKVO(rel.getKvo()), entity);
					entity.setContact(to);
					to.getStates().add(entity);
				} else {
					Contact_ContactState origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getStates().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new Contact_ContactStateKVO(rel.getKvo())
										 , origItem);
					}
				}
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public ContactKVO entityToKvo(Contact entity) {
		ContactKVO kvo = new ContactKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getFirstName() != null && !"".equals(entity.getFirstName())) 
			kvo.setFirstName(entity.getFirstName());  
		if (entity.getLastName() != null && !"".equals(entity.getLastName())) 
			kvo.setLastName(entity.getLastName());  
		if (entity.getAddress() != null && !"".equals(entity.getAddress())) 
			kvo.setAddress(entity.getAddress());  
		if (entity.getCreateDate() != null) 
			kvo.setCreateDate(entity.getCreateDate());
		if (entity.getNumOfAccess() != null) 
			kvo.setNumOfAccess(entity.getNumOfAccess());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getContactTypes() != null)
    			for (ContactCTypeRel item : entity.getContactTypes()) {
    				relationList.add(new ContactCTypeRelMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setContactTypes(ineList);
    		}
		}
		if (entity.getProfilePhoto() != null && !"".equals(entity.getProfilePhoto())) 
			kvo.setProfilePhoto(entity.getProfilePhoto());  
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getNationalities() != null)
    			for (ContactNatRel item : entity.getNationalities()) {
    				relationList.add(new ContactNatRelMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setNationalities(ineList);
    		}
		}
		if (entity.getAddressDetail() != null) 
			kvo.setAddressDetail(new ContactAddresDetailMapper().toRelation(entity.getAddressDetail(), true));
		if (entity.getHappy() != null) 
			kvo.setHappy(entity.getHappy());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getRoles() != null)
    			for (Contact_ContactRole item : entity.getRoles()) {
    				relationList.add(new Contact_ContactRoleMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setRoles(ineList);
    		}
		}
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getStates() != null)
    			for (Contact_ContactState item : entity.getStates()) {
    				relationList.add(new Contact_ContactStateMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setStates(ineList);
    		}
		}

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(Contact entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
	public List<Relation> toRelationList(List<Contact> entityList){
		return toRelationList(entityList, false);
	}
	
	public List<Relation> toRelationList(List<Contact> entityList, boolean includeKvo){
		List<Relation> result = new ArrayList<Relation>();
		for (Contact entity : entityList) {
			result.add(toRelation(entity, includeKvo));
		}
		return result;
	}
	
	public ArrayList<AssistedObject> entityListToKvoList(List<Contact> entityList){
		ArrayList<AssistedObject> result = new ArrayList<AssistedObject>();
		for (Contact o: entityList){
			result.add(entityToKvo(o));
		}
		return result;
	}	
}
