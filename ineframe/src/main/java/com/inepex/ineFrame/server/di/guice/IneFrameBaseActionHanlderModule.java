package com.inepex.ineFrame.server.di.guice;

import net.customware.gwt.dispatch.server.BatchActionHandler;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import net.customware.gwt.dispatch.shared.BatchAction;

import com.inepex.inei18n.server.ChangeLanguageHandler;
import com.inepex.inei18n.server.GetI18nModulesAndSetCurrentLangFromCookieHandler;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;

public class IneFrameBaseActionHanlderModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(BatchAction.class, BatchActionHandler.class);
		bindHandler(ChangeLanguageAction.class, ChangeLanguageHandler.class);
		bindHandler(GetI18nModulesAndSetCurrentLangFromCookieAction.class, GetI18nModulesAndSetCurrentLangFromCookieHandler.class);

	}
}
