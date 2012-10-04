package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory.ContactHandler;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

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
		if (fromHandler.containsString(ContactConsts.k_name)) 
			to.setName(fromHandler.getName());
		if (fromHandler.containsList(ContactConsts.k_phone)) {
			if (to.getPhone() == null)
				to.setPhone(new ArrayList<PhoneNumber>());

    		Map<Long,PhoneNumber> origItems = new HashMap<Long, PhoneNumber>();
			for (PhoneNumber item : to.getPhone()) {
				origItems.put(item.getId(), item);
			}
			
			PhoneNumberMapper mapper = new PhoneNumberMapper(descriptorStore);
			for (Relation rel : fromHandler.getPhone().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					PhoneNumber entity = new PhoneNumber(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity);
										to.getPhone().add(entity);
				} else {
					PhoneNumber origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getPhone().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem);
					}
				}
			}
		}
		if (fromHandler.containsList(ContactConsts.k_email)) {
			if (to.getEmail() == null)
				to.setEmail(new ArrayList<EmailAddress>());

    		Map<Long,EmailAddress> origItems = new HashMap<Long, EmailAddress>();
			for (EmailAddress item : to.getEmail()) {
				origItems.put(item.getId(), item);
			}
			
			EmailAddressMapper mapper = new EmailAddressMapper(descriptorStore);
			for (Relation rel : fromHandler.getEmail().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					EmailAddress entity = new EmailAddress(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity);
										to.getEmail().add(entity);
				} else {
					EmailAddress origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getEmail().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem);
					}
				}
			}
		}
		if (fromHandler.containsRelation(ContactConsts.k_company)) {
			if (fromHandler.getCompany() == null){
				to.setCompany(null);
			} else {
				to.setCompany(new Company(fromHandler.getCompany().getId()));
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
		if (entity.getName() != null && !"".equals(entity.getName())) 
			handler.setName(entity.getName());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getPhone() != null)
    			for (PhoneNumber item : entity.getPhone()) {
    				relationList.add(new PhoneNumberMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setPhone(ineList);
    		}
		}
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getEmail() != null)
    			for (EmailAddress item : entity.getEmail()) {
    				relationList.add(new EmailAddressMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setEmail(ineList);
    		}
		}
		if (entity.getCompany() != null) 
			handler.setCompany(new CompanyMapper(descriptorStore).toRelation(entity.getCompany(), false));

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