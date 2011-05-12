package com.inepex.ineForm.client.form.widgets;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.inepex.ineForm.annotations.Kvo_DisplayName;
import com.inepex.ineForm.annotations.Kvo_SortDefault;


public class Nationality {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Kvo_DisplayName
	@Kvo_SortDefault
	private String name;
	
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
	
	@Override
	public String toString() {
		if (getName() != null)
			return getName();
		return null;
	}
	
	
}
