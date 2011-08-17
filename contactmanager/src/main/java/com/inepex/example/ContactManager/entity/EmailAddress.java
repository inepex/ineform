package com.inepex.example.ContactManager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class EmailAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String email;
	
	public EmailAddress(){
	}
	
	public EmailAddress(Long id){
		this.id=id;
    }
	
	public EmailAddress(String email) {
		super();
		this.email = email;
	}

	@Override
	public String toString() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	
}
