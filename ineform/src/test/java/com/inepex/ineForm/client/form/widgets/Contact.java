package com.inepex.ineForm.client.form.widgets;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.inepex.ineForm.annotations.Kvo_SearchParam;

public class Contact {
	@Id
	Long id;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "contact", orphanRemoval=true)
	@Kvo_SearchParam(secondLevelJoin = "nationality")
	private List<ContactNatRel> nationalities;
	
	public Contact() {

	}

	public Contact(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ContactNatRel> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<ContactNatRel> nationalities) {
		this.nationalities = nationalities;
	}

	
	

}
