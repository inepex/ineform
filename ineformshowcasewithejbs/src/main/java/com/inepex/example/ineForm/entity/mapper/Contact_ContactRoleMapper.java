package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Contact_ContactRole;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleConsts;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactRoleHandlerFactory.Contact_ContactRoleHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class Contact_ContactRoleMapper extends BaseMapper<Contact_ContactRole>{
	
	private final DescriptorStore descriptorStore;
	private final Contact_ContactRoleHandlerFactory handlerFactory;
	
	@Inject
	public Contact_ContactRoleMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new Contact_ContactRoleHandlerFactory(descriptorStore);
	}

	public Contact_ContactRole kvoToEntity(AssistedObject fromKvo, Contact_ContactRole to, CustomKVOObjectDesc... descs) {
		Contact_ContactRoleHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Contact_ContactRole();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(Contact_ContactRoleConsts.k_role)) 
			to.setRole(fromHandler.getRole());
		if (fromHandler.containsLong(Contact_ContactRoleConsts.k_orderNum)) 
			to.setOrderNum(fromHandler.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(Contact_ContactRole entity) {
		Contact_ContactRoleHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getRole() != null && !"".equals(entity.getRole())) 
			handler.setRole(entity.getRole());
		if (entity.getOrderNum() != null) 
			handler.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(Contact_ContactRole entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}