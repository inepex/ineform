package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class RegHandlerFactory implements HandlerFactoryI<RegHandlerFactory.RegHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new RegHandlerFactory(decStore)'
	 */
	@Inject
	public RegHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public RegHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(RegConsts.descriptorName);
		return new RegHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public RegHandler createHandler(AssistedObject assistedObject) {
		if(!RegConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+RegConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new RegHandler(assistedObject, descriptorStore);
	}
	
	public RegSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(RegConsts.searchDescriptor);
		return new RegSearchHandler(assistedObject, descriptorStore);
	}
	
	public RegSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!RegConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+RegConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new RegSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class RegHandler extends AssistedObjectHandler {
	
		private RegHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getEmail() {
        	return getString(RegConsts.k_email);
        }

        public void setEmail(String email) {
    		set(RegConsts.k_email, email);
        }
		
			
        public String getPassword() {
        	return getString(RegConsts.k_password);
        }

        public void setPassword(String password) {
    		set(RegConsts.k_password, password);
        }
		
			
        public String getPasswordAgain() {
        	return getString(RegConsts.k_passwordAgain);
        }

        public void setPasswordAgain(String passwordAgain) {
    		set(RegConsts.k_passwordAgain, passwordAgain);
        }
		
	}

	public static class RegSearchHandler extends AssistedObjectHandler {
	
		private RegSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}