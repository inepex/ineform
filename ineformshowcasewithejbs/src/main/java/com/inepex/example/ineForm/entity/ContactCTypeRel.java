package com.inepex.example.ineForm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;
import com.inepex.ineForm.annotations.Kvo_SortDefault;
import com.inepex.ineForm.annotations.Kvo_Transparent;

@Entity
public class ContactCTypeRel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@Kvo_Transparent
	private Contact contact;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.DETACH})
	@Kvo_SortDefault
	@Kvo_Fetch(mode=Mode.lazy)
	@Kvo_SearchParam
	private ContactType contactType;
	private Long orderNum;
	
	public ContactCTypeRel() {
		// TODO Auto-generated constructor stub
	}

	public ContactCTypeRel(Long id) {
		super();
		this.id = id;
	}
	
	

	public ContactCTypeRel(Long id, Contact contact, ContactType contactType,
			Long orderNum) {
		super();
		this.id = id;
		this.contact = contact;
		this.contactType = contactType;
		this.orderNum = orderNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
}
