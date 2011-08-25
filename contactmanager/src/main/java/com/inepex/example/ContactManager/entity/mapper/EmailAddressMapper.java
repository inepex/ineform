package com.inepex.example.ContactManager.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressConsts;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressHandlerFactory.EmailAddressHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class EmailAddressMapper extends BaseMapper<EmailAddress>{
	
	private final DescriptorStore descriptorStore;
	private final EmailAddressHandlerFactory handlerFactory;
	
	@Inject
	public EmailAddressMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new EmailAddressHandlerFactory(descriptorStore);
	}

	public EmailAddress kvoToEntity(AssistedObject fromKvo, EmailAddress to) {
		EmailAddressHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new EmailAddress();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(EmailAddressConsts.k_email)) 
			to.setEmail(fromHandler.getEmail());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(EmailAddress entity) {
		EmailAddressHandler handler = handlerFactory.createHandler(new KeyValueObject(EmailAddressConsts.descriptorName));
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			handler.setEmail(entity.getEmail());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(EmailAddress entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}