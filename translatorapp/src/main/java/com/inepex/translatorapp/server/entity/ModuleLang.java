package com.inepex.translatorapp.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.inepex.ineForm.annotations.Kvo_Fetch;
import com.inepex.ineForm.annotations.Kvo_Fetch.Mode;
import com.inepex.ineForm.annotations.Kvo_SearchParam;

@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"MODULE_ID", "LANG_ID"}))
public class ModuleLang {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Kvo_SearchParam
	private Long id;
		
	@JoinColumn(nullable=false)
	@ManyToOne
	private Lang lang;
	
	@Kvo_Fetch(mode=Mode.idRelation)
	@JoinColumn(nullable=false)
	private Module module;
	
	public ModuleLang() {
	}
	
	public ModuleLang(Long id) {
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
	
	public Module getModule() {
		return module;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
}
