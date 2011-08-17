package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.kvo.CompanyKVO;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.Relation;

public class CompanyMapper extends BaseMapper<Company>{

	public Company kvoToEntity(AssistedObject fromKvo, Company to) {
		CompanyKVO from = new CompanyKVO(fromKvo);
		if (to == null)
			to = new Company();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(CompanyKVO.k_name)) 
			to.setName(from.getName());
		if (from.containsString(CompanyKVO.k_phone)) 
			to.setPhone(from.getPhone());
		if (from.containsString(CompanyKVO.k_email)) 
			to.setEmail(from.getEmail());
		if (from.containsString(CompanyKVO.k_webPage)) 
			to.setWebPage(from.getWebPage());
		if (from.containsList(CompanyKVO.k_contacts)) {
			if (to.getContacts() == null)
				to.setContacts(new ArrayList<Contact>());

    		Map<Long,Contact> origItems = new HashMap<Long, Contact>();
			for (Contact item : to.getContacts()) {
				origItems.put(item.getId(), item);
			}
			
			ContactMapper mapper = new ContactMapper();
			for (Relation rel : from.getContacts().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					Contact entity = new Contact(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(new ContactKVO(rel.getKvo()), entity);
										to.getContacts().add(entity);
				} else {
					Contact origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getContacts().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(new ContactKVO(rel.getKvo())
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
	
	public CompanyKVO entityToKvo(Company entity) {
		CompanyKVO kvo = new CompanyKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			kvo.setName(entity.getName());  
		if (entity.getPhone() != null && !"".equals(entity.getPhone())) 
			kvo.setPhone(entity.getPhone());  
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			kvo.setEmail(entity.getEmail());  
		if (entity.getWebPage() != null && !"".equals(entity.getWebPage())) 
			kvo.setWebPage(entity.getWebPage());  
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getContacts() != null)
    			for (Contact item : entity.getContacts()) {
    				relationList.add(new ContactMapper().toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			kvo.setContacts(ineList);
    		}
		}

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(Company entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}