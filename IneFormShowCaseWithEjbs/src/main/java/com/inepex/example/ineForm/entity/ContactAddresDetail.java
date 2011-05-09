package com.inepex.example.ineForm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_DisplayName;
import com.inepex.ineForm.annotations.Kvo_SortDefault;

@Entity
public class ContactAddresDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Kvo_DisplayName
	@Kvo_SortDefault
	private String city;
	private String country;
	
	public ContactAddresDetail() {
		// TODO Auto-generated constructor stub
	}

	public ContactAddresDetail(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return getCity();
	}

	
}
