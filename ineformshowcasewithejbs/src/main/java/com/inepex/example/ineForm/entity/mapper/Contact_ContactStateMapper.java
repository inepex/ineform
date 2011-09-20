package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Contact_ContactState;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateConsts;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.Contact_ContactStateHandlerFactory.Contact_ContactStateHandler;
import com.inepex.example.ineForm.enums.ContactState;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class Contact_ContactStateMapper extends BaseMapper<Contact_ContactState>{
	
	private final DescriptorStore descriptorStore;
	private final Contact_ContactStateHandlerFactory handlerFactory;
	
	@Inject
	public Contact_ContactStateMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new Contact_ContactStateHandlerFactory(descriptorStore);
	}

	public Contact_ContactState kvoToEntity(AssistedObject fromKvo, Contact_ContactState to, CustomKVOObjectDesc... descs) {
		Contact_ContactStateHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Contact_ContactState();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
 		if (fromHandler.containsLong(Contact_ContactStateConsts.k_state)) 
			to.setState(ContactState.values()[new Long(fromHandler.getState()).intValue()]);
		if (fromHandler.containsLong(Contact_ContactStateConsts.k_orderNum)) 
			to.setOrderNum(fromHandler.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(Contact_ContactState entity) {
		Contact_ContactStateHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getState() != null){
			handler.setState(new Long(entity.getState().ordinal()));
		}
		if (entity.getOrderNum() != null) 
			handler.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(Contact_ContactState entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}