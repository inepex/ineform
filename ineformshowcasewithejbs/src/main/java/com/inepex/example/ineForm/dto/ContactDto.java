package com.inepex.example.ineForm.dto;


import com.inepex.example.ineForm.entity.kvo.ContactAddresDetailConsts;
import com.inepex.example.ineForm.enums.SpecialContactType;
import com.inepex.ineForm.annotations.Kvo_RelationType;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.DtoAdapter;

public abstract class ContactDto extends DtoAdapter {

	private static final long serialVersionUID = 1L;
		
	private String name;
	private Long relatedRandomValue;
	private String verySecretParameter;
	private SpecialContactType specCont;
	
	@Kvo_RelationType(name=ContactAddresDetailConsts.descriptorName)
	private Relation contactDetails;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRelatedRandomValue() {
		return relatedRandomValue;
	}
	public void setRelatedRandomValue(Long relatedRandomValue) {
		this.relatedRandomValue = relatedRandomValue;
	}
	public String getVerySecretParameter() {
		return verySecretParameter;
	}
	public void setVerySecretParameter(String verySecretParameter) {
		this.verySecretParameter = verySecretParameter;
	}
	public SpecialContactType getSpecCont() {
		return specCont;
	}
	public void setSpecCont(SpecialContactType specCont) {
		this.specCont = specCont;
	}
	public Relation getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(Relation contactDetails) {
		this.contactDetails = contactDetails;
	}
	
}
