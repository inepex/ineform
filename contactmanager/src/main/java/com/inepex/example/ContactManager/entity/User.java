package com.inepex.example.ContactManager.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_SearchParam;
import com.inepex.ineForm.annotations.Kvo_Transparent;
import com.inepex.ineFrame.server.auth.AuthUser;

@Entity
public class User implements AuthUser{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String firstName;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String lastName;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String email;
	
	@Kvo_Transparent
	@Column(nullable=false)
	private String password;
	
	public User(){
	}
	
	public User(Long id){
		this.id=id;
    }

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	@Override
	public Long getUserId() {
		return id;
	}

	@Override
	public String getDisplayName() {
		return firstName+" "+lastName;
	}
	

	@Override
	public String getUserAuthString() {
		return email;
	}

	@Override
	public Set<String> getAllowedRoles() {
		return null;
	}
	 
	
	@Override
	public String toString() {
		return firstName+" "+lastName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	@Override
	public Map<String, String> getUserJsonProps() {
		return null;
	}
}
