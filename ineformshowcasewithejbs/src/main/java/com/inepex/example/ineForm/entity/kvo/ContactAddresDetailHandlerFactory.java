package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactAddresDetailHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ContactAddresDetailHandlerFactory(decStore)'
	 */
	@Inject
	public ContactAddresDetailHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public ContactAddresDetailHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactAddresDetailConsts.descriptorName);
		return new ContactAddresDetailHandler(assistedObject, descriptorStore);
	}
	
	public ContactAddresDetailHandler createHandler(AssistedObject assistedObject) {
		if(!ContactAddresDetailConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactAddresDetailConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactAddresDetailHandler(assistedObject, descriptorStore);
	}
	
	public ContactAddresDetailSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactAddresDetailConsts.searchDescriptor);
		return new ContactAddresDetailSearchHandler(assistedObject, descriptorStore);
	}
	
	public ContactAddresDetailSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ContactAddresDetailConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactAddresDetailConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactAddresDetailSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ContactAddresDetailHandler extends AssistedObjectHandler {
	
		private ContactAddresDetailHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getCity() {
        	return getString(ContactAddresDetailConsts.k_city);
        }

        public void setCity(String city) {
    		set(ContactAddresDetailConsts.k_city, city);
        }
		
			
        public String getCountry() {
        	return getString(ContactAddresDetailConsts.k_country);
        }

        public void setCountry(String country) {
    		set(ContactAddresDetailConsts.k_country, country);
        }
		
	}

	public static class ContactAddresDetailSearchHandler extends AssistedObjectHandler {
	
		private ContactAddresDetailSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}