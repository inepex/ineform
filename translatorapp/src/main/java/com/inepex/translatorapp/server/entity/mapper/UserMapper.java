package com.inepex.translatorapp.server.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.shared.kvo.UserConsts;
import com.inepex.translatorapp.shared.kvo.UserHandlerFactory;
import com.inepex.translatorapp.shared.kvo.UserHandlerFactory.UserHandler;

public class UserMapper extends BaseMapper<User>{
	
	private final DescriptorStore descriptorStore;
	private final UserHandlerFactory handlerFactory;
	
	@Inject
	public UserMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new UserHandlerFactory(descriptorStore);
	}

	@Override
	public User kvoToEntity(AssistedObject fromKvo, User to, CustomKVOObjectDesc... descs) {
		UserHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new User();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(UserConsts.k_email)) 
			to.setEmail(fromHandler.getEmail());
		if (fromHandler.containsString(UserConsts.k_roles)) 
			to.setRoles(fromHandler.getRoles());
		if (fromHandler.containsRelation(UserConsts.k_translates)) {
			if (fromHandler.getTranslates() == null){
				to.setTranslates(null);
			} else {
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	@Override
	public AssistedObject entityToKvo(User entity) {
		UserHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			handler.setEmail(entity.getEmail());
		if (entity.getRoles() != null && !"".equals(entity.getRoles())) 
			handler.setRoles(entity.getRoles());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getTranslates() != null)
    			for (Lang item : entity.getTranslates()) {
    				relationList.add(new LangMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setTranslates(ineList);
    		}
		}

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	@Override
	public Relation toRelation(User entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}