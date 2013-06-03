package com.inepex.translatorapp.shared.action;

import com.inepex.ineForm.shared.dispatch.ObjectListAction;

@SuppressWarnings("serial")
public class RowListAction extends ObjectListAction {

	private Long moduleId = null;
	private String magicString = null;
	
	
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getMagicString() {
		return magicString;
	}
	public void setMagicString(String magicString) {
		this.magicString = magicString;
	}
	
	
	
}
