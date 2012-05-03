package com.inepex.inei18n.server;

import java.util.HashMap;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieResult;
import com.inepex.inei18n.shared.I18nModule;

public class GetI18nModulesAndSetCurrentLangFromCookieHandler implements ActionHandler<GetI18nModulesAndSetCurrentLangFromCookieAction, GetI18nModulesAndSetCurrentLangFromCookieResult>{

	final static Logger logger = LoggerFactory.getLogger(GetI18nModulesAndSetCurrentLangFromCookieHandler.class);
	
	private final I18nStore_Server serverI18nStore;
	private final CurrentLang currentLang;
	private final ChangeLanguageHandler changeLanguageHandler;
	
	@Inject
	public GetI18nModulesAndSetCurrentLangFromCookieHandler(I18nStore_Server serverI18nStore,
			CurrentLang currentLang, ChangeLanguageHandler changeLanguageHandler) {
		this.serverI18nStore = serverI18nStore;
		this.currentLang=currentLang;
		this.changeLanguageHandler=changeLanguageHandler;
	}

	@Override
	public Class<GetI18nModulesAndSetCurrentLangFromCookieAction> getActionType() {
		return GetI18nModulesAndSetCurrentLangFromCookieAction.class;
	}

	@Override
	public GetI18nModulesAndSetCurrentLangFromCookieResult execute(GetI18nModulesAndSetCurrentLangFromCookieAction action, net.customware.gwt.dispatch.server.ExecutionContext arg1)
			throws DispatchException {
		
		if(action.getRequestedLang()!=null)
			changeLanguageHandler.execute(new ChangeLanguageAction(action.getRequestedLang()), arg1);		
		
		HashMap<String, I18nModule> requestedModulesInCurrentLang  = new HashMap<String, I18nModule>(action.getModuleNames().size());
	
		for (String moduleName : action.getModuleNames()) {
			requestedModulesInCurrentLang.put(moduleName, 
					serverI18nStore.getI18nModuleByNameForCurrentLang(moduleName));
		}
		
		logger.info("Requested lang: {}, after action, current lang is: {}", action.getRequestedLang(), currentLang.getCurrentLang());
		
		return new GetI18nModulesAndSetCurrentLangFromCookieResult(currentLang.getCurrentLang()
				, requestedModulesInCurrentLang);
	}

	@Override
	public void rollback(GetI18nModulesAndSetCurrentLangFromCookieAction arg0, GetI18nModulesAndSetCurrentLangFromCookieResult arg1, net.customware.gwt.dispatch.server.ExecutionContext arg2)
			throws DispatchException {
	}
	
}
