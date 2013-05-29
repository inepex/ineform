package com.inepex.translatorapp.server.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.UserLang;
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
		if (fromHandler.containsString(UserConsts.k_role)) 
			to.setRole(fromHandler.getRole());
		if (fromHandler.containsList(UserConsts.k_translates)) {
			if (to.getTranslates() == null)
				to.setTranslates(new ArrayList<UserLang>());

    		Map<Long,UserLang> origItems = new HashMap<Long, UserLang>();
			for (UserLang item : to.getTranslates()) {
				origItems.put(item.getId(), item);
			}
			
			UserLangMapper mapper = new UserLangMapper(descriptorStore);
			for (Relation rel : fromHandler.getTranslates().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					UserLang entity = new UserLang(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity, descs);
					entity.setUser(to);
					to.getTranslates().add(entity);
				} else {
					UserLang origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getTranslates().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem, descs);
					}
				}
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
		if (entity.getRole() != null && !"".equals(entity.getRole())) 
			handler.setRole(entity.getRole());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getTranslates() != null)
    			for (UserLang item : entity.getTranslates()) {
    				relationList.add(new UserLangMapper(descriptorStore).toRelation(item, true));
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