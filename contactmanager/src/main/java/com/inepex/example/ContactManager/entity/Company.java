package com.inepex.example.ContactManager.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.inepex.ineForm.annotations.Kvo_SearchParam;
import com.inepex.ineForm.server.customkvo.CustomKVO;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String name;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String phone;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String email;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String webPage;
	
	@OneToMany(mappedBy="company", cascade={CascadeType.ALL} )
	private List<Contact> contacts;
	
	@JoinColumn(nullable=false)
	@OneToOne(cascade={CascadeType.ALL})
	private CustomKVO extData;
	
	public Company(){
	}
	
	public Company(String name, String phone, String email, String webPage, List<Contact> contacts, CustomKVO extData) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.webPage = webPage;
		this.contacts = contacts;
		this.extData = extData;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public Company(Long id){
		this.id=id;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	public CustomKVO getExtData() {
		return extData;
	}
	
	public void setExtData(CustomKVO extData) {
		this.extData = extData;
	}
}
