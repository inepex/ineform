package com.inepex.example.ContactManager.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<PhoneNumber> phone;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<EmailAddress> email;
	
	@Kvo_SearchParam
	@ManyToOne
	@JoinColumn(nullable=false)
	private Company company;
	
	public Contact(){
	}
	
	public Contact(Long id){
		this.id=id;
    }
	
	public Contact(String name, List<PhoneNumber> phone,
			List<EmailAddress> email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	@Override
	public String toString() {
		return name;
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

	public List<PhoneNumber> getPhone() {
		return phone;
	}

	public void setPhone(List<PhoneNumber> phone) {
		this.phone = phone;
	}

	public List<EmailAddress> getEmail() {
		return email;
	}

	public void setEmail(List<EmailAddress> email) {
		this.email = email;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
