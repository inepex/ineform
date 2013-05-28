package com.inepex.translatorapp.server.entity.mapper;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.LangHandlerFactory;
import com.inepex.translatorapp.shared.kvo.LangHandlerFactory.LangHandler;

public class LangMapper extends BaseMapper<Lang>{
	
	private final LangHandlerFactory handlerFactory;
	
	@Inject
	public LangMapper(DescriptorStore descriptorStore) {
		this.handlerFactory=new LangHandlerFactory(descriptorStore);
	}

	@Override
	public Lang kvoToEntity(AssistedObject fromKvo, Lang to, CustomKVOObjectDesc... descs) {
		LangHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Lang();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(LangConsts.k_isoName)) 
			to.setIsoName(fromHandler.getIsoName());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	@Override
	public AssistedObject entityToKvo(Lang entity) {
		LangHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getIsoName() != null && !"".equals(entity.getIsoName())) 
			handler.setIsoName(entity.getIsoName());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	@Override
	public Relation toRelation(Lang entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}