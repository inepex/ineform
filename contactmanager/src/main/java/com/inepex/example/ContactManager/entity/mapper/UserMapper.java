package com.inepex.example.ContactManager.entity.mapper;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.kvo.UserConsts;
import com.inepex.example.ContactManager.entity.kvo.UserHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.UserHandlerFactory.UserHandler;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class UserMapper extends BaseMapper<User>{
	
	private final DescriptorStore descriptorStore;
	private final UserHandlerFactory handlerFactory;
	
	@Inject
	public UserMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new UserHandlerFactory(descriptorStore);
	}

	public User kvoToEntity(AssistedObject fromKvo, User to, CustomKVOObjectDesc... descs) {
		UserHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new User();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(UserConsts.k_firstName)) 
			to.setFirstName(fromHandler.getFirstName());
		if (fromHandler.containsString(UserConsts.k_lastName)) 
			to.setLastName(fromHandler.getLastName());
		if (fromHandler.containsString(UserConsts.k_email)) 
			to.setEmail(fromHandler.getEmail());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public AssistedObject entityToKvo(User entity) {
		UserHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getFirstName() != null && !"".equals(entity.getFirstName())) 
			handler.setFirstName(entity.getFirstName());
		if (entity.getLastName() != null && !"".equals(entity.getLastName())) 
			handler.setLastName(entity.getLastName());
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			handler.setEmail(entity.getEmail());

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	public Relation toRelation(User entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}