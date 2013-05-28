package com.inepex.translatorapp.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
@Table(uniqueConstraints=
	@UniqueConstraint(columnNames = {"MODULE_ID", "KEY"}))
public class ModuleRow {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
	
	@Kvo_SearchParam
	@Column(nullable=false)
	private String key;
	@Lob
	private String description;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	@Kvo_SearchParam
	@Kvo_Fetch(mode=Mode.lazy)
	private Module module;
	
	@OneToOne
	@JoinColumn(nullable=false)
	@Kvo_Fetch(mode=Mode.eager)
	private TranslatedValue defValue; 
	
	@Kvo_Fetch(mode=Mode.eager)
	@OneToMany(mappedBy="row", cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<TranslatedValue> otherValues = new ArrayList<>();
	
	@Override
	public String toString() {
		return module.getName()+"#"+key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public TranslatedValue getDefValue() {
		return defValue;
	}

	public void setDefValue(TranslatedValue defValue) {
		this.defValue = defValue;
	}

	public List<TranslatedValue> getOtherValues() {
		return otherValues;
	}

	public void setOtherValues(List<TranslatedValue> otherValues) {
		this.otherValues = otherValues;
	}
	
	
}
