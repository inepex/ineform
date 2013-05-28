package com.inepex.translatorapp.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
public class Lang {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false, unique=true)
	private String isoName;
	
	public Lang() {
		
	}
	
	public Lang(String isoName) {
		this.isoName=isoName;
	}

	public Long getId() {
		return id;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIsoName() {
		return isoName;
	}
	
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}
	
	@Override
	public String toString() {
		return isoName;
	}
}
