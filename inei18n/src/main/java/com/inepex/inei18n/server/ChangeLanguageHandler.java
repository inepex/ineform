package com.inepex.inei18n.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

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

	final static Logger logger = LoggerFactory.getLogger(ChangeLanguageHandler.class);
	
	private final CurrentLang currentLang;
	
	@Inject
	public ChangeLanguageHandler(CurrentLang currentLang) {
		this.currentLang = currentLang;
	}
	
	@Override
	public Class<ChangeLanguageAction> getActionType() {
		return ChangeLanguageAction.class;
	}

	@Override
	public SimpleResult execute(ChangeLanguageAction action, net.customware.gwt.dispatch.server.ExecutionContext arg1)
			throws DispatchException {
		
		logger.info("current: "+currentLang.getCurrentLang());
		logger.info("action: "+action.requestedLanguage);
		currentLang.setSessionLang(action.requestedLanguage);
		logger.info("after action: "+currentLang.getCurrentLang());
		
		return new SimpleResult();
	}

	@Override
	public void rollback(ChangeLanguageAction arg0, SimpleResult arg1, net.customware.gwt.dispatch.server.ExecutionContext arg2)
			throws DispatchException {
	}


}
