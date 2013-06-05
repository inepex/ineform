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
	
	
	ObjectList actionForCsvServlet;
	String fileName = "export";
	String tableRDescName = null;
	boolean withHeader = false;
	Renderer renderer = Renderer.CSV;
	boolean appendDate = false;
	
	public SetActionForExportServletAction() {
	}
	
	public SetActionForExportServletAction(
			ObjectListAction actionForCsvServlet
			, String fileName
			, boolean withHeader
	) {
		super();
		this.actionForCsvServlet = actionForCsvServlet;
		this.fileName = fileName;
		this.withHeader = withHeader;
	}

	public SetActionForExportServletAction(
			ObjectList actionForCsvServlet
			, String fileName
			, String tableRDescName
			, boolean withHeader) {
		super();
		this.actionForCsvServlet = actionForCsvServlet;
		this.fileName = fileName;
		this.tableRDescName = tableRDescName;
		this.withHeader = withHeader;
	}
	
	public SetActionForExportServletAction(ObjectList actionForCsvServlet,
			String fileName, String tableRDescName, boolean withHeader,
			Renderer renderer, boolean appendDate) {
		super();
		this.actionForCsvServlet = actionForCsvServlet;
		this.fileName = fileName;
		this.tableRDescName = tableRDescName;
		this.withHeader = withHeader;
		this.renderer = renderer;
		this.appendDate=appendDate;
	}

	public ObjectList getActionForCsvServlet() {
		return actionForCsvServlet;
	}

	public void setActionForCsvServlet(ObjectListAction actionForCsvServlet) {
		this.actionForCsvServlet = actionForCsvServlet;
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
}
