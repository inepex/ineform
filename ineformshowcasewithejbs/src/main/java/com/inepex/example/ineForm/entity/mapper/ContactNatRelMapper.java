package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.ContactNatRel;
import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelConsts;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactNatRelHandlerFactory.ContactNatRelHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactNatRelMapper extends BaseMapper<ContactNatRel>{
	
	private final DescriptorStore descriptorStore;
	private final ContactNatRelHandlerFactory handlerFactory;
	
	@Inject
	public ContactNatRelMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new ContactNatRelHandlerFactory(descriptorStore);
	}

	public ContactNatRel kvoToEntity(AssistedObject fromKvo, ContactNatRel to, CustomKVOObjectDesc... descs) {
		ContactNatRelHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new ContactNatRel();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsRelation(ContactNatRelConsts.k_nationality)) {
			if (fromHandler.getNationality() == null){
				to.setNationality(null);
			} else {
				to.setNationality(new Nationality(fromHandler.getNationality().getId()));
			}
		}
		if (fromHandler.containsLong(ContactNatRelConsts.k_orderNum)) 
			to.setOrderNum(fromHandler.getOrderNum());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(ContactNatRel entity) {
		ContactNatRelHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getNationality() != null) 
			handler.setNationality(new NationalityMapper(descriptorStore).toRelation(entity.getNationality(), false));
		if (entity.getOrderNum() != null) 
			handler.setOrderNum(entity.getOrderNum());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(ContactNatRel entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}