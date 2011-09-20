package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactCTypeRelHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ContactCTypeRelHandlerFactory(decStore)'
	 */
	@Inject
	public ContactCTypeRelHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public ContactCTypeRelHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactCTypeRelConsts.descriptorName);
		return new ContactCTypeRelHandler(assistedObject, descriptorStore);
	}
	
	public ContactCTypeRelHandler createHandler(AssistedObject assistedObject) {
		if(!ContactCTypeRelConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactCTypeRelConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactCTypeRelHandler(assistedObject, descriptorStore);
	}
	
	public ContactCTypeRelSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactCTypeRelConsts.searchDescriptor);
		return new ContactCTypeRelSearchHandler(assistedObject, descriptorStore);
	}
	
	public ContactCTypeRelSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ContactCTypeRelConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactCTypeRelConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactCTypeRelSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ContactCTypeRelHandler extends AssistedObjectHandler {
	
		private ContactCTypeRelHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Relation getContactType() {
    		return getRelation(ContactCTypeRelConsts.k_contactType);
        }	
        public void setContactType(Relation contactType) {
    		set(ContactCTypeRelConsts.k_contactType, contactType);
        }
			
        public Long getOrderNum() {
        	return getLong(ContactCTypeRelConsts.k_orderNum);
        }

        public void setOrderNum(Long orderNum) {
    		set(ContactCTypeRelConsts.k_orderNum, orderNum);
        }
		
	}

	public static class ContactCTypeRelSearchHandler extends AssistedObjectHandler {
	
		private ContactCTypeRelSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}