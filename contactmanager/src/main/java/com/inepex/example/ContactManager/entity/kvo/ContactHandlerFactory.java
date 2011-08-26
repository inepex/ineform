package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ContactHandlerFactory(decStore)'
	 */
	@Inject
	public ContactHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public ContactHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactConsts.descriptorName);
		return new ContactHandler(assistedObject, descriptorStore);
	}
	
	public ContactHandler createHandler(AssistedObject assistedObject) {
		if(!ContactConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactHandler(assistedObject, descriptorStore);
	}
	
	public ContactSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ContactConsts.searchDescriptor);
		return new ContactSearchHandler(assistedObject, descriptorStore);
	}
	
	public ContactSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ContactConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ContactConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ContactSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ContactHandler extends AssistedObjectHandler {
	
		private ContactHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getName() {
        	return getString(ContactConsts.k_name);
        }
        
        public void setName(String name) {
    		set(ContactConsts.k_name, name);
        }
			
    	public IneList getPhone() {
    		return getList(ContactConsts.k_phone);
        }
        public void setPhone(IneList phone) {
    		set(ContactConsts.k_phone, phone);
        }	
			
    	public IneList getEmail() {
    		return getList(ContactConsts.k_email);
        }
        public void setEmail(IneList email) {
    		set(ContactConsts.k_email, email);
        }	
			
        public Relation getCompany() {
    		return getRelation(ContactConsts.k_company);
        }	
        public void setCompany(Relation company) {
    		set(ContactConsts.k_company, company);
        }
	}

	public static class ContactSearchHandler extends AssistedObjectHandler {
	
		private ContactSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getName() {
        	return getString(ContactConsts.s_name);
        }
        
        public void setName(String name) {
    		set(ContactConsts.s_name, name);
        }
        public Relation getCompany() {
    		return getRelation(ContactConsts.s_company);
        }	
        public void setCompany(Relation company) {
    		set(ContactConsts.s_company, company);
        }
	}
}