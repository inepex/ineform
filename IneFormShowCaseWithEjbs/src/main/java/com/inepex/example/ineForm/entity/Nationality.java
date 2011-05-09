package com.inepex.example.ineForm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_DisplayName;
import com.inepex.ineForm.annotations.Kvo_SortDefault;

@Entity
public class Nationality {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Kvo_DisplayName
	@Kvo_SortDefault
	private String name;
	private String description;
	
	public Nationality(){
		
	}
	
	public Nationality(Long id) {
		super();
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		if (getName() != null)
			return getName();
		return null;
	}
	
	
}
