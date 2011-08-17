package com.inepex.example.ContactManager.entity.mapper;

import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.kvo.UserKVO;
import com.inepex.ineForm.server.BaseMapper;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.Relation;

public class UserMapper extends BaseMapper<User>{

	public User kvoToEntity(AssistedObject fromKvo, User to) {
		UserKVO from = new UserKVO(fromKvo);
		if (to == null)
			to = new User();
		if (!from.isNew()) 
			to.setId(from.getId());
		if (from.containsString(UserKVO.k_firstName)) 
			to.setFirstName(from.getFirstName());
		if (from.containsString(UserKVO.k_lastName)) 
			to.setLastName(from.getLastName());
		if (from.containsString(UserKVO.k_email)) 
			to.setEmail(from.getEmail());

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	public UserKVO entityToKvo(User entity) {
		UserKVO kvo = new UserKVO();
		if (entity.getId() != null) 
			kvo.setId(entity.getId());
		if (entity.getFirstName() != null && !"".equals(entity.getFirstName())) 
			kvo.setFirstName(entity.getFirstName());  
		if (entity.getLastName() != null && !"".equals(entity.getLastName())) 
			kvo.setLastName(entity.getLastName());  
		if (entity.getEmail() != null && !"".equals(entity.getEmail())) 
			kvo.setEmail(entity.getEmail());  

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return kvo;
	}
	
	public Relation toRelation(User entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}