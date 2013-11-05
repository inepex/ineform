package com.inepex.ineForm.server.handler;

import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class SetActionForExportServletHandler extends AbstractIneHandler<SetActionForExportServletAction, GenericActionResult> {

	public static final String actionForCsvKey = "actionForCsvKey";
	public static final String filenameForCsvKey = "filenameForCsvKey";
	public static final String appendDateToFileName = "appendDateToFileName";
	public static final String tableRDescForCsvKey = "tableRDescForCsvKey";
	public static final String withHeaderForCsvKey = "withHeaderForCsvKey";
	public static final String rendererForCsvKey = "rendererForCsvKey";
	public static final String rendererLanguage = "rendererLanguage";
	
	private final Provider<HttpServletRequest> requestProvider;
	final Provider<CurrentLang> currLangProvider;

	@Inject
	public SetActionForExportServletHandler(Provider<HttpServletRequest> requestProvider, Provider<CurrentLang> currLangProvider) {
		this.requestProvider = requestProvider;
		this.currLangProvider = currLangProvider;
	}

	@Override
	public Class<SetActionForExportServletAction> getActionType() {
		return SetActionForExportServletAction.class;
	}
	
	@Override
	protected GenericActionResult doExecute(SetActionForExportServletAction action,
			ExecutionContext context) throws AuthenticationException, DispatchException {
		
		HttpServletRequest request = requestProvider.get();
		request.getSession().setAttribute(
				actionForCsvKey
				, action.getActionForCsvServlet());
		request.getSession().setAttribute(
				filenameForCsvKey
				, action.getFileName());
		
		request.getSession().setAttribute(
				appendDateToFileName
				, action.isAppendDate());
		
		if (action.getTableRDescName() != null){
			request.getSession().setAttribute(
					tableRDescForCsvKey
					, action.getTableRDescName());
		}
		
		request.getSession().setAttribute(
				withHeaderForCsvKey
				, action.isWithHeader());
		
		request.getSession().setAttribute(
				rendererForCsvKey
				, action.getRenderer());
		
		request.getSession().setAttribute(
				rendererLanguage
				, currLangProvider.get().getCurrentLang());
		
		return new GenericActionResult();
	}

}
