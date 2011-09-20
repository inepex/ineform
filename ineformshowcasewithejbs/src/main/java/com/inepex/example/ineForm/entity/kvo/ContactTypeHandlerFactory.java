package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactTypeHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ContactTypeHandlerFactory(decStore)'
	 */
	@Inject
	public ContactTypeHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public ContactTypeHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactTypeConsts.descriptorName);
		return new ContactTypeHandler(assistedObject, descriptorStore);
	}
	
	public ContactTypeHandler createHandler(AssistedObject assistedObject) {
		if(!ContactTypeConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactTypeConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactTypeHandler(assistedObject, descriptorStore);
	}
	
	public ContactTypeSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactTypeConsts.searchDescriptor);
		return new ContactTypeSearchHandler(assistedObject, descriptorStore);
	}
	
	public ContactTypeSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ContactTypeConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactTypeConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactTypeSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ContactTypeHandler extends AssistedObjectHandler {
	
		private ContactTypeHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getTypeName() {
        	return getString(ContactTypeConsts.k_typeName);
        }

        public void setTypeName(String typeName) {
    		set(ContactTypeConsts.k_typeName, typeName);
        }
		
			
        public String getDescription() {
        	return getString(ContactTypeConsts.k_description);
        }

        public void setDescription(String description) {
    		set(ContactTypeConsts.k_description, description);
        }
		
	}

	public static class ContactTypeSearchHandler extends AssistedObjectHandler {
	
		private ContactTypeSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getTypeName() {
        	return getString(ContactTypeConsts.s_typeName);
        }
        
        public void setTypeName(String typeName) {
    		set(ContactTypeConsts.s_typeName, typeName);
        }
	}
}