package com.inepex.inei18n.server;

import java.util.HashMap;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.GetI18nModulesAction;
import com.inepex.inei18n.shared.GetI18nModulesResult;
import com.inepex.inei18n.shared.I18nModule;

public class GetI18nModulesHandler implements ActionHandler<GetI18nModulesAction, GetI18nModulesResult>{

	private final I18nStore_Server serverI18nStore;
	private final Provider<CurrentLang> sessionLangProvider;
	
	@Inject
	public GetI18nModulesHandler(I18nStore_Server serverI18nStore, Provider<CurrentLang> sessionLangProvider) {
		this.serverI18nStore = serverI18nStore;
		this.sessionLangProvider = sessionLangProvider;
	}

	@Override
	public Class<GetI18nModulesAction> getActionType() {
		return GetI18nModulesAction.class;
	}

	@Override
	public GetI18nModulesResult execute(GetI18nModulesAction action, net.customware.gwt.dispatch.server.ExecutionContext arg1)
			throws DispatchException {
		Map<String, I18nModule> requestedModulesInCurrentLang 
		= new HashMap<String, I18nModule>(action.moduleNames.size());
	
	for (String moduleName : action.moduleNames) {
		requestedModulesInCurrentLang.put(moduleName, 
				serverI18nStore.getI18nModuleByNameForCurrentLang(moduleName));
	
	}
	
	return new GetI18nModulesResult(sessionLangProvider.get().getCurrentLang()
			, requestedModulesInCurrentLang);
	}

	@Override
	public void rollback(GetI18nModulesAction arg0, GetI18nModulesResult arg1, net.customware.gwt.dispatch.server.ExecutionContext arg2)
			throws DispatchException {
		// TODO Auto-generated method stub
		
	}
	

}
