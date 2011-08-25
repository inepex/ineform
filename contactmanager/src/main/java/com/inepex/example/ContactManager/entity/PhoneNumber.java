package com.inepex.example.ContactManager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class PhoneNumber {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String number;
	
	@Kvo_SearchParam
	@JoinColumn(nullable=false)
	@ManyToOne
	private PhoneNumberType type;
	
	public PhoneNumber(){
	}
	
	public PhoneNumber(Long id){
		this.id=id;
    }
	
	public PhoneNumber(String number, PhoneNumberType type) {
		this.number = number;
		this.type = type;
	}

	@Override
	public String toString() {
		return number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public PhoneNumberType getType() {
		return type;
	}
	
	public void setType(PhoneNumberType type) {
		this.type = type;
	}
	
	
}
