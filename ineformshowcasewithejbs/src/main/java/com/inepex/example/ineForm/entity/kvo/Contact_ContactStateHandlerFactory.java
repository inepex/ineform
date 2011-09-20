package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class Contact_ContactStateHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new Contact_ContactStateHandlerFactory(decStore)'
	 */
	@Inject
	public Contact_ContactStateHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public Contact_ContactStateHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(Contact_ContactStateConsts.descriptorName);
		return new Contact_ContactStateHandler(assistedObject, descriptorStore);
	}
	
	public Contact_ContactStateHandler createHandler(AssistedObject assistedObject) {
		if(!Contact_ContactStateConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+Contact_ContactStateConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new Contact_ContactStateHandler(assistedObject, descriptorStore);
	}
	
	public Contact_ContactStateSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(Contact_ContactStateConsts.searchDescriptor);
		return new Contact_ContactStateSearchHandler(assistedObject, descriptorStore);
	}
	
	public Contact_ContactStateSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!Contact_ContactStateConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+Contact_ContactStateConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new Contact_ContactStateSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class Contact_ContactStateHandler extends AssistedObjectHandler {
	
		private Contact_ContactStateHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Long getState() {
    		return getLong(Contact_ContactStateConsts.k_state);
    	}
        	
        public void setState(Long state) {
    		set(Contact_ContactStateConsts.k_state, state);
        }	
			
        public Long getOrderNum() {
        	return getLong(Contact_ContactStateConsts.k_orderNum);
        }

        public void setOrderNum(Long orderNum) {
    		set(Contact_ContactStateConsts.k_orderNum, orderNum);
        }
		
	}

	public static class Contact_ContactStateSearchHandler extends AssistedObjectHandler {
	
		private Contact_ContactStateSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}