package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class Contact_ContactRoleHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new Contact_ContactRoleHandlerFactory(decStore)'
	 */
	@Inject
	public Contact_ContactRoleHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public Contact_ContactRoleHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(Contact_ContactRoleConsts.descriptorName);
		return new Contact_ContactRoleHandler(assistedObject, descriptorStore);
	}
	
	public Contact_ContactRoleHandler createHandler(AssistedObject assistedObject) {
		if(!Contact_ContactRoleConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+Contact_ContactRoleConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new Contact_ContactRoleHandler(assistedObject, descriptorStore);
	}
	
	public Contact_ContactRoleSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(Contact_ContactRoleConsts.searchDescriptor);
		return new Contact_ContactRoleSearchHandler(assistedObject, descriptorStore);
	}
	
	public Contact_ContactRoleSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!Contact_ContactRoleConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+Contact_ContactRoleConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new Contact_ContactRoleSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class Contact_ContactRoleHandler extends AssistedObjectHandler {
	
		private Contact_ContactRoleHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getRole() {
        	return getString(Contact_ContactRoleConsts.k_role);
        }

        public void setRole(String role) {
    		set(Contact_ContactRoleConsts.k_role, role);
        }
		
			
        public Long getOrderNum() {
        	return getLong(Contact_ContactRoleConsts.k_orderNum);
        }

        public void setOrderNum(Long orderNum) {
    		set(Contact_ContactRoleConsts.k_orderNum, orderNum);
        }
		
	}

	public static class Contact_ContactRoleSearchHandler extends AssistedObjectHandler {
	
		private Contact_ContactRoleSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}