package com.inepex.translatorapp.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class UserLang {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
		
	@JoinColumn(nullable=false)
	@ManyToOne
	private Lang lang;
	
	@JoinColumn(nullable=false)
	@Kvo_Fetch(mode=Mode.idRelation)
	private User user;
	
	public UserLang() {
	}
	
	public UserLang(Long id) {
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lang getLang() {
		return lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
