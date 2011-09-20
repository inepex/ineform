package com.inepex.example.ineForm.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Contact;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.kvo.ContactConsts;
import com.inepex.example.ineForm.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactHandlerFactory.ContactHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactMapper extends BaseMapper<Contact>{
	
	private final DescriptorStore descriptorStore;
	private final ContactHandlerFactory handlerFactory;
	
	@Inject
	public ContactMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new ContactHandlerFactory(descriptorStore);
	}

	public Contact kvoToEntity(AssistedObject fromKvo, Contact to, CustomKVOObjectDesc... descs) {
		ContactHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Contact();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(ContactConsts.k_firstName)) 
			to.setFirstName(fromHandler.getFirstName());
		if (fromHandler.containsString(ContactConsts.k_lastName)) 
			to.setLastName(fromHandler.getLastName());
		if (fromHandler.containsString(ContactConsts.k_address)) 
			to.setAddress(fromHandler.getAddress());
		if (fromHandler.containsLong(ContactConsts.k_createDate)) 
			to.setCreateDate(fromHandler.getCreateDate());
		if (fromHandler.containsLong(ContactConsts.k_numOfAccess)) 
			to.setNumOfAccess(fromHandler.getNumOfAccess());
		if (fromHandler.containsList(ContactConsts.k_contactTypes)) {
			if (to.getContactTypes() == null)
				to.setContactTypes(new ArrayList<ContactCTypeRel>());

    		Map<Long,ContactCTypeRel> origItems = new HashMap<Long, ContactCTypeRel>();
			for (ContactCTypeRel item : to.getContactTypes()) {
				origItems.put(item.getId(), item);
			}
			
			ContactCTypeRelMapper mapper = new ContactCTypeRelMapper(descriptorStore);
			for (Relation rel : fromHandler.getContactTypes().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					ContactCTypeRel entity = new ContactCTypeRel(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity, descs);
										to.getContactTypes().add(entity);
				} else {
					ContactCTypeRel origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getContactTypes().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem, descs);
					}
				}
			}
		}
		if (fromHandler.containsString(ContactConsts.k_profilePhoto)) 
			to.setProfilePhoto(fromHandler.getProfilePhoto());
		if (fromHandler.containsList(ContactConsts.k_nationalities)) {
			if (to.getNationalities() == null)
				to.setNationalities(new ArrayList<ContactNatRel>());

    		Map<Long,ContactNatRel> origItems = new HashMap<Long, ContactNatRel>();
			for (ContactNatRel item : to.getNationalities()) {
				origItems.put(item.getId(), item);
			}
			
			ContactNatRelMapper mapper = new ContactNatRelMapper(descriptorStore);
			for (Relation rel : fromHandler.getNationalities().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					ContactNatRel entity = new ContactNatRel(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity, descs);
										to.getNationalities().add(entity);
				} else {
					ContactNatRel origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getNationalities().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem, descs);
					}
				}
			}
		}
		if (fromHandler.containsRelation(ContactConsts.k_addressDetail)) {
			if (fromHandler.getAddressDetail() == null){
				to.setAddressDetail(null);
			} else {
				ContactAddresDetail relatedEntity = to.getAddressDetail();
    			if (relatedEntity == null) {
					relatedEntity = new ContactAddresDetail(IFConsts.NEW_ITEM_ID);
				}
				new ContactAddresDetailMapper(descriptorStore)
					.kvoToEntity(fromHandler.getAddressDetail().getKvo(), relatedEntity, descs);
				to.setAddressDetail(relatedEntity);
			}
		}
		if (fromHandler.containsBoolean(ContactConsts.k_happy)) 
			to.setHappy(fromHandler.getHappy());
		if (fromHandler.containsList(ContactConsts.k_roles)) {
			if (to.getRoles() == null)
				to.setRoles(new ArrayList<Contact_ContactRole>());

    		Map<Long,Contact_ContactRole> origItems = new HashMap<Long, Contact_ContactRole>();
			for (Contact_ContactRole item : to.getRoles()) {
				origItems.put(item.getId(), item);
			}
			
			Contact_ContactRoleMapper mapper = new Contact_ContactRoleMapper(descriptorStore);
			for (Relation rel : fromHandler.getRoles().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					Contact_ContactRole entity = new Contact_ContactRole(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity, descs);
										to.getRoles().add(entity);
				} else {
					Contact_ContactRole origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getRoles().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem, descs);
					}
				}
			}
		}
		if (fromHandler.containsList(ContactConsts.k_states)) {
			if (to.getStates() == null)
				to.setStates(new ArrayList<Contact_ContactState>());

    		Map<Long,Contact_ContactState> origItems = new HashMap<Long, Contact_ContactState>();
			for (Contact_ContactState item : to.getStates()) {
				origItems.put(item.getId(), item);
			}
			
			Contact_ContactStateMapper mapper = new Contact_ContactStateMapper(descriptorStore);
			for (Relation rel : fromHandler.getStates().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					Contact_ContactState entity = new Contact_ContactState(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity, descs);
										to.getStates().add(entity);
				} else {
					Contact_ContactState origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getStates().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem, descs);
					}
				}
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(Contact entity) {
		ContactHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getFirstName() != null && !"".equals(entity.getFirstName())) 
			handler.setFirstName(entity.getFirstName());
		if (entity.getLastName() != null && !"".equals(entity.getLastName())) 
			handler.setLastName(entity.getLastName());
		if (entity.getAddress() != null && !"".equals(entity.getAddress())) 
			handler.setAddress(entity.getAddress());
		if (entity.getCreateDate() != null) 
			handler.setCreateDate(entity.getCreateDate());
		if (entity.getNumOfAccess() != null) 
			handler.setNumOfAccess(entity.getNumOfAccess());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getContactTypes() != null)
    			for (ContactCTypeRel item : entity.getContactTypes()) {
    				relationList.add(new ContactCTypeRelMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setContactTypes(ineList);
    		}
		}
		if (entity.getProfilePhoto() != null && !"".equals(entity.getProfilePhoto())) 
			handler.setProfilePhoto(entity.getProfilePhoto());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getNationalities() != null)
    			for (ContactNatRel item : entity.getNationalities()) {
    				relationList.add(new ContactNatRelMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setNationalities(ineList);
    		}
		}
		if (entity.getAddressDetail() != null) 
			handler.setAddressDetail(new ContactAddresDetailMapper(descriptorStore).toRelation(entity.getAddressDetail(), true));
		if (entity.getHappy() != null) 
			handler.setHappy(entity.getHappy());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getRoles() != null)
    			for (Contact_ContactRole item : entity.getRoles()) {
    				relationList.add(new Contact_ContactRoleMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setRoles(ineList);
    		}
		}
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getStates() != null)
    			for (Contact_ContactState item : entity.getStates()) {
    				relationList.add(new Contact_ContactStateMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setStates(ineList);
    		}
		}

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(Contact entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}