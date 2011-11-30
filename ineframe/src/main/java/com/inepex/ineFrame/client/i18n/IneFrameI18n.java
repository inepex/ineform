package com.inepex.ineFrame.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class IneFrameI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "IneFrameI18n";
	
	static I18nModuleProvider<IneFrameI18n> moduleProvider;
	
	public IneFrameI18n() {
		super(MODULE_NAME);
	}
		
	public IneFrameI18n(I18nModuleProvider<IneFrameI18n> moduleProvider) {
		super(MODULE_NAME);
		IneFrameI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public String CAPTCHA = "Captcha:";
	public String LOGGINGIN = "Loggin in...";
	public String LOGIN = "Log in!";
	public String LOGOUT = "Log out";
	public String OK = "Ok";
	public String PASSWORD = "Password:";
	public String USERNAME = "User name:";
	public String confirmDialogTitle = "Question";
	public String dayShort = "d";
	public String dialogCancelButton = "Cancel";
	public String dialogOkButton = "Ok";
	public String dummyPageText = "not implemented yet...";
	public String errorDialogTitle = "Error";
	public String generalError = "Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!";
	public String hourShort = "h";
	public String loading = "Loading data...";
	public String minShort = "m";
	public String saveError = "Error occurred during save: <br /> {error}";
	public String saveSuccessful = "Save successful.";
	public String saveUnknownError = "Unknown error occurred during save!";
	public String savingInProgress = "Saving of {savedThing} in progress";
	public String secShort = "s";

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Captcha:
	* <u><i>Magyarul:</i></u> Captcha:
	*/
	public static String CAPTCHA() {
		return moduleProvider.get().CAPTCHA;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loggin in...
	* <u><i>Magyarul:</i></u> Belépés...
	*/
	public static String LOGGINGIN() {
		return moduleProvider.get().LOGGINGIN;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Log in!
	* <u><i>Magyarul:</i></u> Belépés
	*/
	public static String LOGIN() {
		return moduleProvider.get().LOGIN;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Log out
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String LOGOUT() {
		return moduleProvider.get().LOGOUT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String OK() {
		return moduleProvider.get().OK;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password:
	* <u><i>Magyarul:</i></u> Jelszó:
	*/
	public static String PASSWORD() {
		return moduleProvider.get().PASSWORD;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User name:
	* <u><i>Magyarul:</i></u> Felhasználó név:
	*/
	public static String USERNAME() {
		return moduleProvider.get().USERNAME;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Question
	* <u><i>Magyarul:</i></u> Kérdés
	*/
	public static String confirmDialogTitle() {
		return moduleProvider.get().confirmDialogTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> d
	* <u><i>Magyarul:</i></u> n
	*/
	public static String dayShort() {
		return moduleProvider.get().dayShort;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cancel
	* <u><i>Magyarul:</i></u> Mégse
	*/
	public static String dialogCancelButton() {
		return moduleProvider.get().dialogCancelButton;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String dialogOkButton() {
		return moduleProvider.get().dialogOkButton;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> not implemented yet...
	* <u><i>Magyarul:</i></u> készülőben...
	*/
	public static String dummyPageText() {
		return moduleProvider.get().dummyPageText;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error
	* <u><i>Magyarul:</i></u> Hiba
	*/
	public static String errorDialogTitle() {
		return moduleProvider.get().errorDialogTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!
	* <u><i>Magyarul:</i></u> A szerveren nem várt hiba lépett fel! Próbálja meg újratölteni az oldalt. Ha a hibajelenséget továbbra is tapasztalja kérem értesítse a rendszergazdát!
	*/
	public static String generalError() {
		return moduleProvider.get().generalError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> h
	* <u><i>Magyarul:</i></u> ó
	*/
	public static String hourShort() {
		return moduleProvider.get().hourShort;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().loading;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> m
	* <u><i>Magyarul:</i></u> p
	*/
	public static String minShort() {
		return moduleProvider.get().minShort;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error occurred during save: <br /> {error}
	* <u><i>Magyarul:</i></u> A mentés során az alábbi hiba történt: <br /> {error}
	*/
	public static String saveError(String error) {
		return moduleProvider.get().saveError.replace("{error}", error);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Save successful.
	* <u><i>Magyarul:</i></u> Sikeresen elmentve
	*/
	public static String saveSuccessful() {
		return moduleProvider.get().saveSuccessful;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unknown error occurred during save!
	* <u><i>Magyarul:</i></u> A mentés során ismeretlen hiba történt.
	*/
	public static String saveUnknownError() {
		return moduleProvider.get().saveUnknownError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saving of {savedThing} in progress
	* <u><i>Magyarul:</i></u> A(z) {savedThing} mentése folyamatban
	*/
	public static String savingInProgress(String savedThing) {
		return moduleProvider.get().savingInProgress.replace("{savedThing}", savedThing);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> s
	* <u><i>Magyarul:</i></u> mp
	*/
	public static String secShort() {
		return moduleProvider.get().secShort;
	}
}
