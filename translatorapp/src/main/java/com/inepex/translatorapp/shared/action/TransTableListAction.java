package com.inepex.translatorapp.shared.action;

import com.inepex.ineForm.shared.dispatch.ObjectListAction;

@SuppressWarnings("serial")
public class TransTableListAction extends ObjectListAction {
	
	private String moduleName = null;
	private TranslateListingType listType = TranslateListingType.All;
	
	public void setListType(TranslateListingType listType) {
		this.listType = listType;
	}
	
	public TranslateListingType getListType() {
		return listType;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
