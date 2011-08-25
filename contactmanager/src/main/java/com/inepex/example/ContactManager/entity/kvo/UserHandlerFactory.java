package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class UserHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new UserHandlerFactory(decStore)'
	 */
	@Inject
	public UserHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public UserHandler createHandler(AssistedObject assistedObject) {
		if(!UserConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+UserConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new UserHandler(assistedObject, descriptorStore);
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
	
			
        public String getFirstName() {
        	return getString(UserConsts.k_firstName);
        }
        
        public void setFirstName(String firstName) {
    		set(UserConsts.k_firstName, firstName);
        }
			
        public String getLastName() {
        	return getString(UserConsts.k_lastName);
        }
        
        public void setLastName(String lastName) {
    		set(UserConsts.k_lastName, lastName);
        }
			
        public String getEmail() {
        	return getString(UserConsts.k_email);
        }
        
        public void setEmail(String email) {
    		set(UserConsts.k_email, email);
        }
	}

	public static class UserSearchHandler extends AssistedObjectHandler {
	
		private UserSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getFirstName() {
        	return getString(UserConsts.s_firstName);
        }
        
        public void setFirstName(String firstName) {
    		set(UserConsts.s_firstName, firstName);
        }
        public String getLastName() {
        	return getString(UserConsts.s_lastName);
        }
        
        public void setLastName(String lastName) {
    		set(UserConsts.s_lastName, lastName);
        }
        public String getEmail() {
        	return getString(UserConsts.s_email);
        }
        
        public void setEmail(String email) {
    		set(UserConsts.s_email, email);
        }
	}
}