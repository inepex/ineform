package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactTypeConsts;
import com.inepex.example.ineForm.entity.kvo.ContactTypeHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactTypeHandlerFactory.ContactTypeHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactTypeMapper extends BaseMapper<ContactType>{
	
	private final DescriptorStore descriptorStore;
	private final ContactTypeHandlerFactory handlerFactory;
	
	@Inject
	public ContactTypeMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new ContactTypeHandlerFactory(descriptorStore);
	}

	public ContactType kvoToEntity(AssistedObject fromKvo, ContactType to, CustomKVOObjectDesc... descs) {
		ContactTypeHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new ContactType();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(ContactTypeConsts.k_typeName)) 
			to.setTypeName(fromHandler.getTypeName());
		if (fromHandler.containsString(ContactTypeConsts.k_description)) 
			to.setDescription(fromHandler.getDescription());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(ContactType entity) {
		ContactTypeHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getTypeName() != null && !"".equals(entity.getTypeName())) 
			handler.setTypeName(entity.getTypeName());
		if (entity.getDescription() != null && !"".equals(entity.getDescription())) 
			handler.setDescription(entity.getDescription());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(ContactType entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}