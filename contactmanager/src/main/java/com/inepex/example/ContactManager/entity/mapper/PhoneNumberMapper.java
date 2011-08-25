package com.inepex.example.ContactManager.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.PhoneNumber;
import com.inepex.example.ContactManager.entity.PhoneNumberType;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberConsts;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.PhoneNumberHandlerFactory.PhoneNumberHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class PhoneNumberMapper extends BaseMapper<PhoneNumber>{
	
	private final DescriptorStore descriptorStore;
	private final PhoneNumberHandlerFactory handlerFactory;
	
	@Inject
	public PhoneNumberMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new PhoneNumberHandlerFactory(descriptorStore);
	}

	public PhoneNumber kvoToEntity(AssistedObject fromKvo, PhoneNumber to) {
		PhoneNumberHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new PhoneNumber();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(PhoneNumberConsts.k_number)) 
			to.setNumber(fromHandler.getNumber());
		if (fromHandler.containsRelation(PhoneNumberConsts.k_type)) {
			if (fromHandler.getType() == null){
				to.setType(null);
			} else {
				to.setType(new PhoneNumberType(fromHandler.getType().getId()));
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(PhoneNumber entity) {
		PhoneNumberHandler handler = handlerFactory.createHandler(new KeyValueObject(PhoneNumberConsts.descriptorName));
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getNumber() != null && !"".equals(entity.getNumber())) 
			handler.setNumber(entity.getNumber());  
		if (entity.getType() != null) 
			handler.setType(new PhoneNumberTypeMapper(descriptorStore).toRelation(entity.getType(), false));

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(PhoneNumber entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}