package com.inepex.ineForm.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;


public class SetActionForExportServletAction implements Action<GenericActionResult>{

	public enum Renderer implements IsSerializable  {
		CSV
		, HTML
		, TRTD
		, PDF
		, EXCEL
	}
	
	
	private ObjectList objectList;
	private String fileName = "export";
	private String tableRDescName = null;
	private boolean withHeader = false;
	private Renderer renderer = Renderer.CSV;
	private boolean appendDate = false;
	private String responseDescriptorName;
	
	public SetActionForExportServletAction() {
	}
	
	public SetActionForExportServletAction(
			ObjectListAction objectList
			, String fileName
			, boolean withHeader
	) {
		super();
		this.objectList = objectList;
		this.fileName = fileName;
		this.withHeader = withHeader;
	}

	public SetActionForExportServletAction(
			ObjectList objectList
			, String fileName
			, String tableRDescName
			, boolean withHeader) {
		super();
		this.objectList = objectList;
		this.fileName = fileName;
		this.tableRDescName = tableRDescName;
		this.withHeader = withHeader;
	}
	
	public SetActionForExportServletAction(ObjectList objectList,
										   String fileName, 
										   String tableRDescName, 
										   boolean withHeader,
										   Renderer renderer, 
										   boolean appendDate,
										   String responseDescriptorName) {
		super();
		this.objectList = objectList;
		this.fileName = fileName;
		this.tableRDescName = tableRDescName;
		this.withHeader = withHeader;
		this.renderer = renderer;
		this.appendDate=appendDate;
		this.responseDescriptorName = responseDescriptorName;
	}

	public ObjectList getActionForCsvServlet() {
		return objectList;
	}

	public void setActionForCsvServlet(ObjectListAction actionForCsvServlet) {
		this.objectList = actionForCsvServlet;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTableRDescName() {
		return tableRDescName;
	}

	public void setTableRDescName(String tableRDescName) {
		this.tableRDescName = tableRDescName;
	}

	public boolean isWithHeader() {
		return withHeader;
	}

	public void setWithHeader(boolean withHeader) {
		this.withHeader = withHeader;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}	
	
	public void setAppendDate(boolean appendDate) {
		this.appendDate = appendDate;
	}
	
	public boolean isAppendDate() {
		return appendDate;
	}

	public String getResponseDescriptorName() {
		return responseDescriptorName;
	}

	public void setResponseDescriptorName(String responseDescriptorName) {
		this.responseDescriptorName = responseDescriptorName;
	}
}
