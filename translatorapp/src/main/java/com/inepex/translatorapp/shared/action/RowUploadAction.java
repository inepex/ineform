package com.inepex.translatorapp.shared.action;

import com.inepex.ineom.shared.dispatch.GenericActionResult;

import net.customware.gwt.dispatch.shared.Action;

public class RowUploadAction implements Action<GenericActionResult>{
	
	private Long moduleId;
	private String header;
	private String rows;
	
	public RowUploadAction() {
	}
	
	public RowUploadAction(Long moduleId, String header, String rows) {
		this.header = header;
		this.rows = rows;
		this.moduleId=moduleId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public Long getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
}
