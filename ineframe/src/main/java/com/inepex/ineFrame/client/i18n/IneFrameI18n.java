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
	public String LOGGINGIN = "Logging in...";
	public String LOGIN = "Login";
	public String LOGOUT = "Log out";
	public String OK = "Ok";
	public String PASSWORD = "Password:";
	public String USERNAME = "User name:";
	public String confirmDialogTitle = "Question";
	public String dayShort = "d";
	public String daysAgo = "{number} days ago";
	public String dialogCancelButton = "Cancel";
	public String dialogOkButton = "Ok";
	public String dummyPageText = "not implemented yet...";
	public String errorDialogTitle = "Error";
	public String generalError = "Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!";
	public String hourShort = "h";
	public String hoursAgo = "{number} hours ago";
	public String inemenu_apps = "Apps";
	public String inemenu_help = "Help";
	public String inemenu_helpsettings = "HELP & SETTINGS";
	public String inemenu_leavealias = "Leave alias";
	public String inemenu_logout = "Logout";
	public String inemenu_settings = "Settings";
	public String loading = "Loading data...";
	public String minShort = "m";
	public String minsAgo = "{number} mins ago";
	public String oneDayAgo = "one day ago";
	public String oneHourAgo = "one hour ago";
	public String oneMinAgo = "one min ago";
	public String reconnected = "Connected";
	public String reconnection = "No network connection. Reconnect in {delay} ({reconnectionTime}).";
	public String saveError = "Error occurred during save: <br /> {error}";
	public String saveSuccessful = "Save successful.";
	public String saveUnknownError = "Unknown error occurred during save!";
	public String savingInProgress = "Saving of {savedThing} in progress";
	public String secSAgo = "{number} secs ago";
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
	* <u><i>In English:</i></u> Logging in...
	* <u><i>Magyarul:</i></u> Belépés...
	*/
	public static String LOGGINGIN() {
		return moduleProvider.get().LOGGINGIN;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Login
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
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} days ago
	* <u><i>Magyarul:</i></u> {number} napja
	*/
	public static String daysAgo(String number) {
		return moduleProvider.get().daysAgo.replace("{number}", number);
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
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} hours ago
	* <u><i>Magyarul:</i></u> {number} órája
	*/
	public static String hoursAgo(String number) {
		return moduleProvider.get().hoursAgo.replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Apps
	* <u><i>Magyarul:</i></u> Appok
	*/
	public static String inemenu_apps() {
		return moduleProvider.get().inemenu_apps;
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help
	* <u><i>Magyarul:</i></u> Súgó
	*/
	public static String inemenu_help() {
		return moduleProvider.get().inemenu_help;
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> HELP & SETTINGS
	* <u><i>Magyarul:</i></u> SÚGÓ, BEÁLLÍTÁSOK
	*/
	public static String inemenu_helpsettings() {
		return moduleProvider.get().inemenu_helpsettings;
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Leave alias
	* <u><i>Magyarul:</i></u> Alias mód elhagyása
	*/
	public static String inemenu_leavealias() {
		return moduleProvider.get().inemenu_leavealias;
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Logout
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String inemenu_logout() {
		return moduleProvider.get().inemenu_logout;
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Settings
	* <u><i>Magyarul:</i></u> Beállítások
	*/
	public static String inemenu_settings() {
		return moduleProvider.get().inemenu_settings;
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
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} mins ago
	* <u><i>Magyarul:</i></u> {number} perce
	*/
	public static String minsAgo(String number) {
		return moduleProvider.get().minsAgo.replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one day ago
	* <u><i>Magyarul:</i></u> egy napja
	*/
	public static String oneDayAgo() {
		return moduleProvider.get().oneDayAgo;
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one hour ago
	* <u><i>Magyarul:</i></u> egy órája
	*/
	public static String oneHourAgo() {
		return moduleProvider.get().oneHourAgo;
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one min ago
	* <u><i>Magyarul:</i></u> egy perce
	*/
	public static String oneMinAgo() {
		return moduleProvider.get().oneMinAgo;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Connected
	* <u><i>Magyarul:</i></u> Csatlakozva
	*/
	public static String reconnected() {
		return moduleProvider.get().reconnected;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No network connection. Reconnect in {delay} ({reconnectionTime}).
	* <u><i>Magyarul:</i></u> Nincs kapcsolat. Újracsatlakozás {delay} múlva ({reconnectionTime}).
	*/
	public static String reconnection(String delay, String reconnectionTime) {
		return moduleProvider.get().reconnection.replace("{delay}", delay).replace("{reconnectionTime}", reconnectionTime);
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
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} secs ago
	* <u><i>Magyarul:</i></u> {number} másodperce
	*/
	public static String secSAgo(String number) {
		return moduleProvider.get().secSAgo.replace("{number}", number);
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
