package com.inepex.example.ContactManager.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.Company;
import com.inepex.example.ContactManager.entity.Contact;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.CompanyHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.CompanyHandlerFactory.CompanyHandler;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class CompanyMapper extends BaseMapper<Company>{
	
	private final DescriptorStore descriptorStore;
	private final CompanyHandlerFactory handlerFactory;
	
	@Inject
	public CompanyMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new CompanyHandlerFactory(descriptorStore);
	}

	public Company kvoToEntity(AssistedObject fromKvo, Company to) {
		CompanyHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Company();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(CompanyConsts.k_name)) 
			to.setName(fromHandler.getName());
		if (fromHandler.containsString(CompanyConsts.k_phone)) 
			to.setPhone(fromHandler.getPhone());
		if (fromHandler.containsString(CompanyConsts.k_email)) 
			to.setEmail(fromHandler.getEmail());
		if (fromHandler.containsString(CompanyConsts.k_webPage)) 
			to.setWebPage(fromHandler.getWebPage());
		if (fromHandler.containsList(CompanyConsts.k_contacts)) {
			if (to.getContacts() == null)
				to.setContacts(new ArrayList<Contact>());

    		Map<Long,Contact> origItems = new HashMap<Long, Contact>();
			for (Contact item : to.getContacts()) {
				origItems.put(item.getId(), item);
			}
			
			ContactMapper mapper = new ContactMapper(descriptorStore);
			for (Relation rel : fromHandler.getContacts().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					Contact entity = new Contact(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity);
										to.getContacts().add(entity);
				} else {
					Contact origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getContacts().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem);
					}
				}
			}
		}
    	if(fromHandler.containsRelation(CompanyConsts.k_extData)) {
    			CustomKVO custKvo = to.getExtData();
    			if(custKvo==null) {
    				custKvo=new CustomKVO();
    				to.setExtData(custKvo);
    			}
    			
    			ObjectDesc od = null;
    			for(CustomKVOObjectDesc desc : descs) 
    				if(CompanyConsts.k_extData.equals(desc.getKey()))
    						od=desc;
    				
    			CustomKVOMapperHelper.mapIntoCustomKVO(custKvo, fromHandler.getExtData().getKvo(), od);
    		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(Company entity) {
		CompanyHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			handler.setName(entity.getName());
		if (entity.getPhone() != null && !"".equals(entity.getPhone())) 
			handler.setPhone(entity.getPhone());
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			handler.setEmail(entity.getEmail());
		if (entity.getWebPage() != null && !"".equals(entity.getWebPage())) 
			handler.setWebPage(entity.getWebPage());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getContacts() != null)
    			for (Contact item : entity.getContacts()) {
    				relationList.add(new ContactMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setContacts(ineList);
    		}
		}
		if(entity.getExtData() != null)
			handler.setExtData(new Relation(CustomKVOMapperHelper.getKVOFromCustomKVO(entity.getExtData())));

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(Company entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}