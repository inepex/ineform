package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class NationalityHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new NationalityHandlerFactory(decStore)'
	 */
	@Inject
	public NationalityHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public NationalityHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(NationalityConsts.descriptorName);
		return new NationalityHandler(assistedObject, descriptorStore);
	}
	
	public NationalityHandler createHandler(AssistedObject assistedObject) {
		if(!NationalityConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+NationalityConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new NationalityHandler(assistedObject, descriptorStore);
	}
	
	public NationalitySearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(NationalityConsts.searchDescriptor);
		return new NationalitySearchHandler(assistedObject, descriptorStore);
	}
	
	public NationalitySearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!NationalityConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+NationalityConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new NationalitySearchHandler(assistedObject, descriptorStore);
	}
	
	public static class NationalityHandler extends AssistedObjectHandler {
	
		private NationalityHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getName() {
        	return getString(NationalityConsts.k_name);
        }

        public void setName(String name) {
    		set(NationalityConsts.k_name, name);
        }
		
			
        public String getDescription() {
        	return getString(NationalityConsts.k_description);
        }

        public void setDescription(String description) {
    		set(NationalityConsts.k_description, description);
        }
		
	}

	public static class NationalitySearchHandler extends AssistedObjectHandler {
	
		private NationalitySearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}