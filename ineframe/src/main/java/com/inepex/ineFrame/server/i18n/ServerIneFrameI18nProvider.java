package com.inepex.ineFrame.server.i18n;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerIneFrameI18nProvider extends ServerI18nProvider<IneFrameI18n> {

	private static final long serialVersionUID = 1L;

	public ServerIneFrameI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<IneFrameI18n> getModuleClass() {
		return IneFrameI18n.class;
	}

	@Override
	public IneFrameI18n getVirgineI18nModule() {
		IneFrameI18n module = new IneFrameI18n(this);
		initTexts(module);
		return module;
	}
	
	public void initTexts(IneFrameI18n module){
		module.getTextMap().put("CAPTCHA", "Captcha:");
		module.getTextMap().put("LOGGINGIN", "Logging in...");
		module.getTextMap().put("LOGIN", "Login");
		module.getTextMap().put("LOGOUT", "Log out");
		module.getTextMap().put("OK", "Ok");
		module.getTextMap().put("PASSWORD", "Password:");
		module.getTextMap().put("USERNAME", "User name:");
		module.getTextMap().put("confirmDialogTitle", "Question");
		module.getTextMap().put("dayShort", "d");
		module.getTextMap().put("daysAgo", "{number} days ago");
		module.getTextMap().put("dialogCancelButton", "Cancel");
		module.getTextMap().put("dialogOkButton", "Ok");
		module.getTextMap().put("dummyPageText", "not implemented yet...");
		module.getTextMap().put("errorDialogTitle", "Error");
		module.getTextMap().put("generalError", "Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!");
		module.getTextMap().put("hourShort", "h");
		module.getTextMap().put("hoursAgo", "{number} hours ago");
		module.getTextMap().put("inemenu_apps", "Apps");
		module.getTextMap().put("inemenu_help", "Help");
		module.getTextMap().put("inemenu_helpsettings", "Help & Settings");
		module.getTextMap().put("inemenu_leavealias", "Leave alias");
		module.getTextMap().put("inemenu_logout", "Logout");
		module.getTextMap().put("inemenu_settings", "Settings");
		module.getTextMap().put("loading", "Loading data...");
		module.getTextMap().put("minShort", "m");
		module.getTextMap().put("minsAgo", "{number} mins ago");
		module.getTextMap().put("oneDayAgo", "one day ago");
		module.getTextMap().put("oneHourAgo", "one hour ago");
		module.getTextMap().put("oneMinAgo", "one min ago");
		module.getTextMap().put("reconnected", "Connected");
		module.getTextMap().put("reconnection", "No network connection. We will try to reconnect in a few seconds.");
		module.getTextMap().put("saveError", "Error occurred during save: <br /> {error}");
		module.getTextMap().put("saveSuccessful", "Save successful.");
		module.getTextMap().put("saveUnknownError", "Unknown error occurred during save!");
		module.getTextMap().put("savingInProgress", "Saving of {savedThing} in progress");
		module.getTextMap().put("secSAgo", "{number} secs ago");
		module.getTextMap().put("secShort", "s");
	}
}
