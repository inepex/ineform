package com.inepex.example.ContactManager.entity.kvo;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class CompanyHandlerFactory { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new CompanyHandlerFactory(decStore)'
	 */
	@Inject
	public CompanyHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	public CompanyHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(CompanyConsts.descriptorName);
		return new CompanyHandler(assistedObject, descriptorStore);
	}
	
	public CompanyHandler createHandler(AssistedObject assistedObject) {
		if(!CompanyConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+CompanyConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new CompanyHandler(assistedObject, descriptorStore);
	}
	
	public CompanySearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(CompanyConsts.searchDescriptor);
		return new CompanySearchHandler(assistedObject, descriptorStore);
	}
	
	public CompanySearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!CompanyConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+CompanyConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new CompanySearchHandler(assistedObject, descriptorStore);
	}
	
	public static class CompanyHandler extends AssistedObjectHandler {
	
		private CompanyHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getName() {
        	return getString(CompanyConsts.k_name);
        }

        public void setName(String name) {
    		set(CompanyConsts.k_name, name);
        }
		
			
        public String getPhone() {
        	return getString(CompanyConsts.k_phone);
        }

        public void setPhone(String phone) {
    		set(CompanyConsts.k_phone, phone);
        }
		
			
        public String getEmail() {
        	return getString(CompanyConsts.k_email);
        }

        public void setEmail(String email) {
    		set(CompanyConsts.k_email, email);
        }
		
			
        public String getWebPage() {
        	return getString(CompanyConsts.k_webPage);
        }

        public void setWebPage(String webPage) {
    		set(CompanyConsts.k_webPage, webPage);
        }
		
			
    	public IneList getContacts() {
    		return getList(CompanyConsts.k_contacts);
        }
        public void setContacts(IneList contacts) {
    		set(CompanyConsts.k_contacts, contacts);
        }	
			
		public Relation getExtData() {
    		return getRelation(CompanyConsts.k_extData);
        }	
		
		public void setExtData(Relation extData) {
    		set(CompanyConsts.k_extData, extData);
        }
	
	}

	public static class CompanySearchHandler extends AssistedObjectHandler {
	
		private CompanySearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getName() {
        	return getString(CompanyConsts.s_name);
        }
        
        public void setName(String name) {
    		set(CompanyConsts.s_name, name);
        }
        public String getPhone() {
        	return getString(CompanyConsts.s_phone);
        }
        
        public void setPhone(String phone) {
    		set(CompanyConsts.s_phone, phone);
        }
        public String getEmail() {
        	return getString(CompanyConsts.s_email);
        }
        
        public void setEmail(String email) {
    		set(CompanyConsts.s_email, email);
        }
        public String getWebPage() {
        	return getString(CompanyConsts.s_webPage);
        }
        
        public void setWebPage(String webPage) {
    		set(CompanyConsts.s_webPage, webPage);
        }
	}
}