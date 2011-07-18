package com.inepex.ineFrame.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class IneFrameI18n_old extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "IneFrameI18n_old";
	
	static I18nModuleProvider<IneFrameI18n_old> moduleProvider;
	
	public IneFrameI18n_old() {
		super(MODULE_NAME);
	}
		
	public IneFrameI18n_old(I18nModuleProvider<IneFrameI18n_old> moduleProvider) {
		super(MODULE_NAME);
		IneFrameI18n_old.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public String LOGIN = "Log in!";
	public String LOGOUT = "Log out";
	public String OK = "Ok";
	public String PASSWORD = "Password:";
	public String USERNAME = "User name:";
	public String confirmDialogTitle = "Question";
	public String dialogCancelButton = "Cancel";
	public String dialogOkButton = "Ok";
	public String errorDialogTitle = "Error";
	public String generalError = "Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!";
	public String loading = "Loading data...";
	public String saveError = "Error occurred during save: <br /> {error}";
	public String saveSuccessful = "Save successful.";
	public String saveUnknownError = "Unknown error occurred during save!";
	public String savingInProgress = "Saving of {savedThing} in progress";

	
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
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().loading;
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
}
