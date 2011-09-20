package com.inepex.example.ineForm.entity.kvo;

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
	
			
        public String getFirstName() {
        	return getString(ContactConsts.k_firstName);
        }

        public void setFirstName(String firstName) {
    		set(ContactConsts.k_firstName, firstName);
        }
		
			
        public String getLastName() {
        	return getString(ContactConsts.k_lastName);
        }

        public void setLastName(String lastName) {
    		set(ContactConsts.k_lastName, lastName);
        }
		
			
        public String getAddress() {
        	return getString(ContactConsts.k_address);
        }

        public void setAddress(String address) {
    		set(ContactConsts.k_address, address);
        }
		
			
        public Long getCreateDate() {
        	return getLong(ContactConsts.k_createDate);
        }

        public void setCreateDate(Long createDate) {
    		set(ContactConsts.k_createDate, createDate);
        }
		
			
        public Long getNumOfAccess() {
        	return getLong(ContactConsts.k_numOfAccess);
        }

        public void setNumOfAccess(Long numOfAccess) {
    		set(ContactConsts.k_numOfAccess, numOfAccess);
        }
		
			
    	public IneList getContactTypes() {
    		return getList(ContactConsts.k_contactTypes);
        }
        public void setContactTypes(IneList contactTypes) {
    		set(ContactConsts.k_contactTypes, contactTypes);
        }	
			
        public String getProfilePhoto() {
        	return getString(ContactConsts.k_profilePhoto);
        }

        public void setProfilePhoto(String profilePhoto) {
    		set(ContactConsts.k_profilePhoto, profilePhoto);
        }
		
			
    	public IneList getNationalities() {
    		return getList(ContactConsts.k_nationalities);
        }
        public void setNationalities(IneList nationalities) {
    		set(ContactConsts.k_nationalities, nationalities);
        }	
			
        public Relation getAddressDetail() {
    		return getRelation(ContactConsts.k_addressDetail);
        }	
        public void setAddressDetail(Relation addressDetail) {
    		set(ContactConsts.k_addressDetail, addressDetail);
        }
			
        public Boolean getHappy() {
        	return getBoolean(ContactConsts.k_happy);
        }

        public void setHappy(Boolean happy) {
    		set(ContactConsts.k_happy, happy);
        }
		
			
    	public IneList getRoles() {
    		return getList(ContactConsts.k_roles);
        }
        public void setRoles(IneList roles) {
    		set(ContactConsts.k_roles, roles);
        }	
			
    	public IneList getStates() {
    		return getList(ContactConsts.k_states);
        }
        public void setStates(IneList states) {
    		set(ContactConsts.k_states, states);
        }	
	}

	public static class ContactSearchHandler extends AssistedObjectHandler {
	
		private ContactSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getFirstName() {
        	return getString(ContactConsts.s_firstName);
        }
        
        public void setFirstName(String firstName) {
    		set(ContactConsts.s_firstName, firstName);
        }
        public String getLastName() {
        	return getString(ContactConsts.s_lastName);
        }
        
        public void setLastName(String lastName) {
    		set(ContactConsts.s_lastName, lastName);
        }
    	public IneList getContactTypes() {
    		return getList(ContactConsts.s_contactTypes);
        }
        public void setContactTypes(IneList contactTypes) {
    		set(ContactConsts.s_contactTypes, contactTypes);
        }	
    	public IneList getNationalities() {
    		return getList(ContactConsts.s_nationalities);
        }
        public void setNationalities(IneList nationalities) {
    		set(ContactConsts.s_nationalities, nationalities);
        }	
        public Boolean getHappy() {
        	return getBoolean(ContactConsts.s_happy);
        }
        
        public void setHappy(Boolean happy) {
    		set(ContactConsts.s_happy, happy);
        }
    	public IneList getRoles() {
    		return getList(ContactConsts.s_roles);
        }
        public void setRoles(IneList roles) {
    		set(ContactConsts.s_roles, roles);
        }	
    	public IneList getStates() {
    		return getList(ContactConsts.s_states);
        }
        public void setStates(IneList states) {
    		set(ContactConsts.s_states, states);
        }	
	}
}