package com.inepex.example.ineForm.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.Nationality;
import com.inepex.example.ineForm.entity.kvo.NationalityConsts;
import com.inepex.example.ineForm.entity.kvo.NationalityHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.NationalityHandlerFactory.NationalityHandler;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class NationalityMapper extends BaseMapper<Nationality>{
	
	private final DescriptorStore descriptorStore;
	private final NationalityHandlerFactory handlerFactory;
	
	@Inject
	public NationalityMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new NationalityHandlerFactory(descriptorStore);
	}

	public Nationality kvoToEntity(AssistedObject fromKvo, Nationality to, CustomKVOObjectDesc... descs) {
		NationalityHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Nationality();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(NationalityConsts.k_name)) 
			to.setName(fromHandler.getName());
		if (fromHandler.containsString(NationalityConsts.k_description)) 
			to.setDescription(fromHandler.getDescription());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(Nationality entity) {
		NationalityHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			handler.setName(entity.getName());
		if (entity.getDescription() != null && !"".equals(entity.getDescription())) 
			handler.setDescription(entity.getDescription());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(Nationality entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}