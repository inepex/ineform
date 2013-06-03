package com.inepex.translatorapp.server.entity.mapper;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory.TranslatedValueHandler;

public class TranslatedValueMapper extends BaseMapper<TranslatedValue>{
	
	private final DescriptorStore descriptorStore;
	private final TranslatedValueHandlerFactory handlerFactory;
	
	@Inject
	public TranslatedValueMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new TranslatedValueHandlerFactory(descriptorStore);
	}

	@Override
	public TranslatedValue kvoToEntity(AssistedObject fromKvo, TranslatedValue to, CustomKVOObjectDesc... descs) {
		TranslatedValueHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new TranslatedValue();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsLong(TranslatedValueConsts.k_lastModTime)) 
			to.setLastModTime(fromHandler.getLastModTime());
		if (fromHandler.containsRelation(TranslatedValueConsts.k_lastModUser)) {
			if (fromHandler.getLastModUser() == null){
				to.setLastModUser(null);
			} else {
				to.setLastModUser(new User(fromHandler.getLastModUser().getId()));
			}
		}
		if (fromHandler.containsString(TranslatedValueConsts.k_value)) 
			to.setValue(fromHandler.getValue());
		if (fromHandler.containsRelation(TranslatedValueConsts.k_lang)) {
			if (fromHandler.getLang() == null){
				to.setLang(null);
			} else {
				to.setLang(new Lang(fromHandler.getLang().getId()));
			}
		}
		if (fromHandler.containsRelation(TranslatedValueConsts.k_row)) {
			if (fromHandler.getRow() == null){
				to.setRow(null);
			} else {
				to.setRow(new ModuleRow(fromHandler.getRow().getId()));
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	@Override
	public AssistedObject entityToKvo(TranslatedValue entity) {
		TranslatedValueHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getLastModTime() != null) 
			handler.setLastModTime(entity.getLastModTime());
		if (entity.getLastModUser() != null) 
			handler.setLastModUser(new UserMapper(descriptorStore).toRelation(entity.getLastModUser(), false));
		if (entity.getValue() != null && !"".equals(entity.getValue())) 
			handler.setValue(entity.getValue());
		if (entity.getLang() != null) 
			handler.setLang(new LangMapper(descriptorStore).toRelation(entity.getLang(), true));
		if (entity.getRow() != null) 
			handler.setRow(new ModuleRowMapper(descriptorStore).toRelation(entity.getRow(), false));

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	@Override
	public Relation toRelation(TranslatedValue entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}