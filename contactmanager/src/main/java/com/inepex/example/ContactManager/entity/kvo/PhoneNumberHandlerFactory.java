package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class PhoneNumberHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new PhoneNumberHandlerFactory(decStore)'
	 */
	@Inject
	public PhoneNumberHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public PhoneNumberHandler createHandler(AssistedObject assistedObject) {
		if(!PhoneNumberConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+PhoneNumberConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new PhoneNumberHandler(assistedObject, descriptorStore);
	}
	
	public PhoneNumberSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!PhoneNumberConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+PhoneNumberConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new PhoneNumberSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class PhoneNumberHandler extends AssistedObjectHandler {
	
		private PhoneNumberHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getNumber() {
        	return getString(PhoneNumberConsts.k_number);
        }
        
        public void setNumber(String number) {
    		set(PhoneNumberConsts.k_number, number);
        }
			
        public Relation getType() {
    		return getRelation(PhoneNumberConsts.k_type);
        }	
        public void setType(Relation type) {
    		set(PhoneNumberConsts.k_type, type);
        }
	}

	public static class PhoneNumberSearchHandler extends AssistedObjectHandler {
	
		private PhoneNumberSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getNumber() {
        	return getString(PhoneNumberConsts.s_number);
        }
        
        public void setNumber(String number) {
    		set(PhoneNumberConsts.s_number, number);
        }
        public Relation getType() {
    		return getRelation(PhoneNumberConsts.s_type);
        }	
        public void setType(Relation type) {
    		set(PhoneNumberConsts.s_type, type);
        }
	}
}