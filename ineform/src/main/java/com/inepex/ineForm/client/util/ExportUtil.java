package com.inepex.ineForm.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction.Renderer;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.dialog.InfoDialog;
import com.inepex.ineom.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.kvo.IFConsts;

public class ExportUtil {

	public static class ExportClickHandler implements ClickHandler{

		ObjectListAction action;
		String fileName;
		IneDispatch dispatcher;
		Renderer rendererType;
		Boolean withHeader;
		Boolean appendDateToFileName;
				
		public ExportClickHandler(
				IneDispatch dispatcher
				, ObjectListAction action
				, String fileName
				, Renderer rendererType
				, Boolean withHeader
				, Boolean appendDateToFileName) {
			super();
			this.action = action;
			this.fileName = fileName;
			this.dispatcher = dispatcher;
			this.rendererType = rendererType;
			this.withHeader = withHeader;
			this.appendDateToFileName=appendDateToFileName;
		}

		@Override
		public void onClick(ClickEvent event) {
			setActionForExportAndOpenDownloadDialog(
					dispatcher
					, action
					, fileName
					, null
					, withHeader
					, rendererType
					, appendDateToFileName);
		}
		
	}
	
	/**
	 * 
	 * @param dispatcher
	 * @param action
	 * @param fileName
	 * @param tableRDescName set null to use default tablerenderdescriptor
	 * @param withHeader
	 */
	public static void setActionForExportAndOpenDownloadDialog(
			IneDispatch dispatcher
			, ObjectListAction action
			, String fileName
			, String tableRDescName
			, boolean withHeader
			, Renderer rendererType
			, boolean appendDateToFileName){
		dispatcher.execute(new SetActionForExportServletAction(
				action
				, fileName
				, tableRDescName
				, withHeader
				, rendererType
				, appendDateToFileName)
		, new SuccessCallback<GenericResult>(){

			@Override
			public void onSuccess(GenericResult result) {
				if (Window.Navigator.getUserAgent().contains("MSIE")){
					
					new InfoDialog(IneFormI18n.CSVEXPORT(), IneFormI18n.csvComment() + "<a href = '"+ IFConsts.exportServletUrl +"'>" + IneFormI18n.csvDownload() + "</a>");
				} else {
					Window.open(IFConsts.exportServletUrl, "CSV export", "_blank");
				}
			}
			
		});
	}
	
}
