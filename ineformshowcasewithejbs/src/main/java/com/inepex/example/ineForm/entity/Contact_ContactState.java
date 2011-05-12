package com.inepex.example.ineForm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.inepex.example.ineForm.enums.ContactState;
import com.inepex.ineForm.annotations.Kvo_Transparent;

@Entity
public class Contact_ContactState {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@ManyToOne
	@Kvo_Transparent
	Contact contact;

	ContactState state;
	
	private Long orderNum;
	
	public Contact_ContactState() {
	}

	public Contact_ContactState(Long id) {
		super();
		this.id = id;
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

	public ContactState getState() {
		return state;
	}

	public void setState(ContactState state) {
		this.state = state;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	
}
