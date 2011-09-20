package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.ContactCTypeRel;
import com.inepex.example.ineForm.entity.ContactType;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelConsts;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactCTypeRelHandlerFactory.ContactCTypeRelHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactCTypeRelMapper extends BaseMapper<ContactCTypeRel>{
	
	private final DescriptorStore descriptorStore;
	private final ContactCTypeRelHandlerFactory handlerFactory;
	
	@Inject
	public ContactCTypeRelMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new ContactCTypeRelHandlerFactory(descriptorStore);
	}

	public ContactCTypeRel kvoToEntity(AssistedObject fromKvo, ContactCTypeRel to, CustomKVOObjectDesc... descs) {
		ContactCTypeRelHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new ContactCTypeRel();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsRelation(ContactCTypeRelConsts.k_contactType)) {
			if (fromHandler.getContactType() == null){
				to.setContactType(null);
			} else {
				to.setContactType(new ContactType(fromHandler.getContactType().getId()));
			}
		}
		if (fromHandler.containsLong(ContactCTypeRelConsts.k_orderNum)) 
			to.setOrderNum(fromHandler.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(ContactCTypeRel entity) {
		ContactCTypeRelHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getContactType() != null) 
			handler.setContactType(new ContactTypeMapper(descriptorStore).toRelation(entity.getContactType(), true));
		if (entity.getOrderNum() != null) 
			handler.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(ContactCTypeRel entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}