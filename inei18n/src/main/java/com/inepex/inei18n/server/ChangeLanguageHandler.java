package com.inepex.inei18n.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.SimpleResult;

/**
 * 
 * see {@link ChangeLanguageAction} for details
 * 
 */
public class ChangeLanguageHandler implements ActionHandler<ChangeLanguageAction, SimpleResult> {

	private final static Logger logger = LoggerFactory.getLogger(ChangeLanguageHandler.class);
	
	private final CurrentLang currentLang;
	private final I18nStore_Server i18nStore;
	
	@Inject
	public ChangeLanguageHandler(CurrentLang currentLang, I18nStore_Server i18nStore) {
		this.currentLang = currentLang;
		this.i18nStore=i18nStore;
	}
	
	@Override
	public Class<ChangeLanguageAction> getActionType() {
		return ChangeLanguageAction.class;
	}

	@Override
	public SimpleResult execute(ChangeLanguageAction action, net.customware.gwt.dispatch.server.ExecutionContext arg1)
			throws DispatchException {
		
		String reqLang = null;
		if(action.requestedLanguage==null || "".equals(action.requestedLanguage) || !i18nStore.getAllLangs().contains(action.requestedLanguage)) {
			reqLang=CurrentLang.DEFAULT_LANG;
			logger.warn("Requested lang is not supported or null: {}", action.requestedLanguage);
		} else {
			reqLang=action.requestedLanguage;
		}
		
		currentLang.setSessionLang(reqLang);
		return new SimpleResult();
	}

	@Override
	public void rollback(ChangeLanguageAction arg0, SimpleResult arg1, net.customware.gwt.dispatch.server.ExecutionContext arg2)
			throws DispatchException {
	}


}
