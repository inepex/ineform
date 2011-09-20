package com.inepex.example.ineForm.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactNatRelHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ContactNatRelHandlerFactory(decStore)'
	 */
	@Inject
	public ContactNatRelHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public ContactNatRelHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactNatRelConsts.descriptorName);
		return new ContactNatRelHandler(assistedObject, descriptorStore);
	}
	
	public ContactNatRelHandler createHandler(AssistedObject assistedObject) {
		if(!ContactNatRelConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactNatRelConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactNatRelHandler(assistedObject, descriptorStore);
	}
	
	public ContactNatRelSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactNatRelConsts.searchDescriptor);
		return new ContactNatRelSearchHandler(assistedObject, descriptorStore);
	}
	
	public ContactNatRelSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ContactNatRelConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactNatRelConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactNatRelSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ContactNatRelHandler extends AssistedObjectHandler {
	
		private ContactNatRelHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Relation getNationality() {
    		return getRelation(ContactNatRelConsts.k_nationality);
        }	
        public void setNationality(Relation nationality) {
    		set(ContactNatRelConsts.k_nationality, nationality);
        }
			
        public Long getOrderNum() {
        	return getLong(ContactNatRelConsts.k_orderNum);
        }

        public void setOrderNum(Long orderNum) {
    		set(ContactNatRelConsts.k_orderNum, orderNum);
        }
		
	}

	public static class ContactNatRelSearchHandler extends AssistedObjectHandler {
	
		private ContactNatRelSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}