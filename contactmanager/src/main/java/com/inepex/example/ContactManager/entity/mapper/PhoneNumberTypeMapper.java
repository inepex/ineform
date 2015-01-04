package com.inepex.example.ContactManager.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeConsts;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberTypeHandlerFactory.PhoneNumberTypeHandler;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class PhoneNumberTypeMapper extends BaseMapper<PhoneNumberType>{
	
	private final DescriptorStore descriptorStore;
	private final PhoneNumberTypeHandlerFactory handlerFactory;
	
	@Inject
	public PhoneNumberTypeMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new PhoneNumberTypeHandlerFactory(descriptorStore);
	}

	public PhoneNumberType kvoToEntity(AssistedObject fromKvo, PhoneNumberType to) {
		PhoneNumberTypeHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new PhoneNumberType();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(PhoneNumberTypeConsts.k_name)) 
			to.setName(fromHandler.getName());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(PhoneNumberType entity) {
		PhoneNumberTypeHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			handler.setName(entity.getName());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(PhoneNumberType entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}