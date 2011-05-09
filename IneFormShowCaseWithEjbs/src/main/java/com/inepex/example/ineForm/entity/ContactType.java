package com.inepex.example.ineForm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_SearchParam;
import com.inepex.ineForm.annotations.Kvo_SortDefault;

@Entity
public class ContactType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Kvo_SearchParam
	@Kvo_SortDefault
	String typeName;
	String description;
	
	public ContactType() {
	}
	
	public ContactType(Long id) {
		this.id = id;
	}
	
	public ContactType(Long id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return typeName;
	}
}
