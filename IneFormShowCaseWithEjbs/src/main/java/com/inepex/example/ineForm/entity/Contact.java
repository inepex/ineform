package com.inepex.example.ineForm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_SearchParam;
import com.inepex.ineForm.annotations.Kvo_SortDefault;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Kvo_SearchParam @Kvo_SortDefault
	String firstName;
	
	@Kvo_SearchParam
	String lastName;
	String address;
	Long createDate;
	Long numOfAccess;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "contact", orphanRemoval=true, fetch=FetchType.EAGER)
	@Kvo_SearchParam(secondLevelJoin = "contactType")
	List<ContactCTypeRel> contactTypes;
	
	String profilePhoto;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "contact", orphanRemoval=true)
	@Kvo_SearchParam(secondLevelJoin = "nationality")
	private List<ContactNatRel> nationalities;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Kvo_Fetch
	ContactAddresDetail addressDetail;
	
	@Kvo_SearchParam
	Boolean happy;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "contact", orphanRemoval=true, fetch=FetchType.EAGER)
	@Kvo_SearchParam(secondLevelJoin = "role")
	List<Contact_ContactRole> roles;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "contact", orphanRemoval=true, fetch=FetchType.EAGER)
	@Kvo_SearchParam(secondLevelJoin = "state")
	List<Contact_ContactState> states;
	
	public Contact() {

	}

	public Contact(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getNumOfAccess() {
		return numOfAccess;
	}

	public void setNumOfAccess(Long numOfAccess) {
		this.numOfAccess = numOfAccess;
	}


	public List<ContactCTypeRel> getContactTypes() {
		return contactTypes;
	}

	public void setContactTypes(List<ContactCTypeRel> contactTypes) {
		this.contactTypes = contactTypes;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public List<ContactNatRel> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<ContactNatRel> nationalities) {
		this.nationalities = nationalities;
	}

	public ContactAddresDetail getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(ContactAddresDetail addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Boolean getHappy() {
		return happy;
	}

	public void setHappy(Boolean happy) {
		this.happy = happy;
	}

	public List<Contact_ContactRole> getRoles() {
		return roles;
	}

	public void setRoles(List<Contact_ContactRole> roles) {
		this.roles = roles;
	}

	public List<Contact_ContactState> getStates() {
		return states;
	}

	public void setStates(List<Contact_ContactState> states) {
		this.states = states;
	}

}
