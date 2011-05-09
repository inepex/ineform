package com.inepex.inei18n.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.SimpleResult;

public class ChangeLanguageHandler implements ActionHandler<ChangeLanguageAction, SimpleResult> {

	private final Provider<CurrentLang> sessionLangProvider;
	
	@Inject
	public ChangeLanguageHandler(Provider<CurrentLang> sessionLangProvider) {
		this.sessionLangProvider = sessionLangProvider;
	}
	
	@Override
	public Class<ChangeLanguageAction> getActionType() {
		return ChangeLanguageAction.class;
	}

	@Override
	public SimpleResult execute(ChangeLanguageAction action, net.customware.gwt.dispatch.server.ExecutionContext arg1)
			throws DispatchException {
		if (sessionLangProvider.get() instanceof ServerCurrentLang)
			((ServerCurrentLang) sessionLangProvider.get()).setSessionLang(action.requestedLanguage);
		return new SimpleResult();
	}

	@Override
	public void rollback(ChangeLanguageAction arg0, SimpleResult arg1, net.customware.gwt.dispatch.server.ExecutionContext arg2)
			throws DispatchException {
		// TODO Auto-generated method stub
		
	}


}
