package com.inepex.translatorapp.server.entity.mapper;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.UserLang;
import com.inepex.translatorapp.shared.kvo.UserLangConsts;
import com.inepex.translatorapp.shared.kvo.UserLangHandlerFactory;
import com.inepex.translatorapp.shared.kvo.UserLangHandlerFactory.UserLangHandler;

public class UserLangMapper extends BaseMapper<UserLang>{
	
	private final DescriptorStore descriptorStore;
	private final UserLangHandlerFactory handlerFactory;
	
	@Inject
	public UserLangMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new UserLangHandlerFactory(descriptorStore);
	}

	@Override
	public UserLang kvoToEntity(AssistedObject fromKvo, UserLang to) {
		UserLangHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new UserLang();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsRelation(UserLangConsts.k_lang)) {
			if (fromHandler.getLang() == null){
				to.setLang(null);
			} else {
				to.setLang(new Lang(fromHandler.getLang().getId()));
			}
		}
		if (fromHandler.containsRelation(UserLangConsts.k_user)) {
			if (fromHandler.getUser() == null){
				to.setUser(null);
			} else {
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	@Override
	public AssistedObject entityToKvo(UserLang entity) {
		UserLangHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getLang() != null) 
			handler.setLang(new LangMapper(descriptorStore).toRelation(entity.getLang(), true));
		if (entity.getUser() != null) 
			handler.setUser(new UserMapper(descriptorStore).toRelation(entity.getUser(), false));

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	@Override
	public Relation toRelation(UserLang entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}