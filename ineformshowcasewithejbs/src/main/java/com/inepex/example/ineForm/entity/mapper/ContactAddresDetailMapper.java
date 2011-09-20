package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.ContactAddresDetail;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailConsts;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailHandlerFactory.ContactAddresDetailHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactAddresDetailMapper extends BaseMapper<ContactAddresDetail>{
	
	private final DescriptorStore descriptorStore;
	private final ContactAddresDetailHandlerFactory handlerFactory;
	
	@Inject
	public ContactAddresDetailMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new ContactAddresDetailHandlerFactory(descriptorStore);
	}

	public ContactAddresDetail kvoToEntity(AssistedObject fromKvo, ContactAddresDetail to, CustomKVOObjectDesc... descs) {
		ContactAddresDetailHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new ContactAddresDetail();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(ContactAddresDetailConsts.k_city)) 
			to.setCity(fromHandler.getCity());
		if (fromHandler.containsString(ContactAddresDetailConsts.k_country)) 
			to.setCountry(fromHandler.getCountry());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(ContactAddresDetail entity) {
		ContactAddresDetailHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getCity() != null && !"".equals(entity.getCity())) 
			handler.setCity(entity.getCity());
		if (entity.getCountry() != null && !"".equals(entity.getCountry())) 
			handler.setCountry(entity.getCountry());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(ContactAddresDetail entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}