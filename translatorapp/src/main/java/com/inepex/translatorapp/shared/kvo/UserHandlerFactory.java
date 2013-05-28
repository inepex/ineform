package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class UserHandlerFactory implements HandlerFactoryI<UserHandlerFactory.UserHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new UserHandlerFactory(decStore)'
	 */
	@Inject
	public UserHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public UserHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(UserConsts.descriptorName);
		return new UserHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public UserHandler createHandler(AssistedObject assistedObject) {
		if(!UserConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+UserConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new UserHandler(assistedObject, descriptorStore);
	}
	
	public UserSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(UserConsts.searchDescriptor);
		return new UserSearchHandler(assistedObject, descriptorStore);
	}
	
	public UserSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!UserConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+UserConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new UserSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class UserHandler extends AssistedObjectHandler {
	
		private UserHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getEmail() {
        	return getString(UserConsts.k_email);
        }

        public void setEmail(String email) {
    		set(UserConsts.k_email, email);
        }
		
			
        public String getRoles() {
        	return getString(UserConsts.k_roles);
        }

        public void setRoles(String roles) {
    		set(UserConsts.k_roles, roles);
        }
		
			
    	public IneList getTranslates() {
    		return getList(UserConsts.k_translates);
        }
        public void setTranslates(IneList translates) {
    		set(UserConsts.k_translates, translates);
        }	
	}

	public static class UserSearchHandler extends AssistedObjectHandler {
	
		private UserSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getEmail() {
        	return getString(UserConsts.s_email);
        }
        
        public void setEmail(String email) {
    		set(UserConsts.s_email, email);
        }
	}
}