package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressKVO;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class ContactMapper extends BaseMapper<Contact>{

	public Contact kvoToEntity(AssistedObject fromKvo, Contact to) {
		ContactKVO from = new ContactKVO(fromKvo);
		if (to == null)
			to = new Contact();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(ContactKVO.k_name)) 
			to.setName(from.getName());
		if (from.containsList(ContactKVO.k_phone)) {
			if (to.getPhone() == null)
				to.setPhone(new ArrayList<PhoneNumber>());

    		Map<Long,PhoneNumber> origItems = new HashMap<Long, PhoneNumber>();
			for (PhoneNumber item : to.getPhone()) {
				origItems.put(item.getId(), item);
			}
			
			PhoneNumberMapper mapper = new PhoneNumberMapper();
			for (Relation rel : from.getPhone().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					PhoneNumber entity = new PhoneNumber(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new PhoneNumberKVO(rel.getKvo()), entity);
										to.getPhone().add(entity);
				} else {
					PhoneNumber origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getPhone().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new PhoneNumberKVO(rel.getKvo())
										 , origItem);
					}
				}
			}
		}
		if (from.containsList(ContactKVO.k_email)) {
			if (to.getEmail() == null)
				to.setEmail(new ArrayList<EmailAddress>());

    		Map<Long,EmailAddress> origItems = new HashMap<Long, EmailAddress>();
			for (EmailAddress item : to.getEmail()) {
				origItems.put(item.getId(), item);
			}
			
			EmailAddressMapper mapper = new EmailAddressMapper();
			for (Relation rel : from.getEmail().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					EmailAddress entity = new EmailAddress(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new EmailAddressKVO(rel.getKvo()), entity);
										to.getEmail().add(entity);
				} else {
					EmailAddress origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getEmail().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new EmailAddressKVO(rel.getKvo())
										 , origItem);
					}
				}
			}
		}
		if (from.containsRelation(ContactKVO.k_company)) {
			if (from.getCompany() == null){
				to.setCompany(null);
			} else {
				to.setCompany(new Company(from.getCompany().getId()));
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
		if (entity.getName() != null && !"".equals(entity.getName())) 
			kvo.setName(entity.getName());  
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getPhone() != null)
    			for (PhoneNumber item : entity.getPhone()) {
    				relationList.add(new PhoneNumberMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setPhone(ineList);
    		}
		}
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getEmail() != null)
    			for (EmailAddress item : entity.getEmail()) {
    				relationList.add(new EmailAddressMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setEmail(ineList);
    		}
		}
		if (entity.getCompany() != null) 
			kvo.setCompany(new CompanyMapper().toRelation(entity.getCompany(), false));

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
	
}