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
	
	public void init(){
		textMap.put("CAPTCHA", "Captcha:");
		textMap.put("LOGGINGIN", "Logging in...");
		textMap.put("LOGIN", "Login");
		textMap.put("LOGOUT", "Log out");
		textMap.put("OK", "Ok");
		textMap.put("PASSWORD", "Password:");
		textMap.put("USERNAME", "User name:");
		textMap.put("confirmDialogTitle", "Question");
		textMap.put("dayShort", "d");
		textMap.put("daysAgo", "{number} days ago");
		textMap.put("dialogCancelButton", "Cancel");
		textMap.put("dialogOkButton", "Ok");
		textMap.put("dummyPageText", "not implemented yet...");
		textMap.put("errorDialogTitle", "Error");
		textMap.put("generalError", "Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!");
		textMap.put("hourShort", "h");
		textMap.put("hoursAgo", "{number} hours ago");
		textMap.put("inemenu_apps", "Apps");
		textMap.put("inemenu_help", "Help");
		textMap.put("inemenu_helpsettings", "Help & Settings");
		textMap.put("inemenu_leavealias", "Leave alias");
		textMap.put("inemenu_logout", "Logout");
		textMap.put("inemenu_settings", "Settings");
		textMap.put("loading", "Loading data...");
		textMap.put("minShort", "m");
		textMap.put("minsAgo", "{number} mins ago");
		textMap.put("oneDayAgo", "one day ago");
		textMap.put("oneHourAgo", "one hour ago");
		textMap.put("oneMinAgo", "one min ago");
		textMap.put("reconnected", "Connected");
		textMap.put("reconnection", "No network connection. We will try to reconnect in a few seconds.");
		textMap.put("saveError", "Error occurred during save: <br /> {error}");
		textMap.put("saveSuccessful", "Save successful.");
		textMap.put("saveUnknownError", "Unknown error occurred during save!");
		textMap.put("savingInProgress", "Saving of {savedThing} in progress");
		textMap.put("secSAgo", "{number} secs ago");
		textMap.put("secShort", "s");
	}

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Captcha:
	* <u><i>Magyarul:</i></u> Captcha:
	*/
	public static String CAPTCHA() {
		return moduleProvider.get().getTextMap().get("CAPTCHA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Logging in...
	* <u><i>Magyarul:</i></u> Belépés...
	*/
	public static String LOGGINGIN() {
		return moduleProvider.get().getTextMap().get("LOGGINGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Login
	* <u><i>Magyarul:</i></u> Belépés
	*/
	public static String LOGIN() {
		return moduleProvider.get().getTextMap().get("LOGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Log out
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String LOGOUT() {
		return moduleProvider.get().getTextMap().get("LOGOUT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String OK() {
		return moduleProvider.get().getTextMap().get("OK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password:
	* <u><i>Magyarul:</i></u> Jelszó:
	*/
	public static String PASSWORD() {
		return moduleProvider.get().getTextMap().get("PASSWORD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User name:
	* <u><i>Magyarul:</i></u> Felhasználó név:
	*/
	public static String USERNAME() {
		return moduleProvider.get().getTextMap().get("USERNAME");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Question
	* <u><i>Magyarul:</i></u> Kérdés
	*/
	public static String confirmDialogTitle() {
		return moduleProvider.get().getTextMap().get("confirmDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> d
	* <u><i>Magyarul:</i></u> n
	*/
	public static String dayShort() {
		return moduleProvider.get().getTextMap().get("dayShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} days ago
	* <u><i>Magyarul:</i></u> {number} napja
	*/
	public static String daysAgo(String number) {
		return moduleProvider.get().getTextMap().get("daysAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cancel
	* <u><i>Magyarul:</i></u> Mégse
	*/
	public static String dialogCancelButton() {
		return moduleProvider.get().getTextMap().get("dialogCancelButton");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String dialogOkButton() {
		return moduleProvider.get().getTextMap().get("dialogOkButton");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> not implemented yet...
	* <u><i>Magyarul:</i></u> készülőben...
	*/
	public static String dummyPageText() {
		return moduleProvider.get().getTextMap().get("dummyPageText");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error
	* <u><i>Magyarul:</i></u> Hiba
	*/
	public static String errorDialogTitle() {
		return moduleProvider.get().getTextMap().get("errorDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!
	* <u><i>Magyarul:</i></u> A szerveren nem várt hiba lépett fel! Próbálja meg újratölteni az oldalt. Ha a hibajelenséget továbbra is tapasztalja kérem értesítse a rendszergazdát!
	*/
	public static String generalError() {
		return moduleProvider.get().getTextMap().get("generalError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> h
	* <u><i>Magyarul:</i></u> ó
	*/
	public static String hourShort() {
		return moduleProvider.get().getTextMap().get("hourShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} hours ago
	* <u><i>Magyarul:</i></u> {number} órája
	*/
	public static String hoursAgo(String number) {
		return moduleProvider.get().getTextMap().get("hoursAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Apps
	* <u><i>Magyarul:</i></u> Appok
	*/
	public static String inemenu_apps() {
		return moduleProvider.get().getTextMap().get("inemenu_apps");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help
	* <u><i>Magyarul:</i></u> Súgó
	*/
	public static String inemenu_help() {
		return moduleProvider.get().getTextMap().get("inemenu_help");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help & Settings
	* <u><i>Magyarul:</i></u> Súgó, beállítások
	*/
	public static String inemenu_helpsettings() {
		return moduleProvider.get().getTextMap().get("inemenu_helpsettings");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Leave alias
	* <u><i>Magyarul:</i></u> Alias mód elhagyása
	*/
	public static String inemenu_leavealias() {
		return moduleProvider.get().getTextMap().get("inemenu_leavealias");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Logout
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String inemenu_logout() {
		return moduleProvider.get().getTextMap().get("inemenu_logout");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Settings
	* <u><i>Magyarul:</i></u> Beállítások
	*/
	public static String inemenu_settings() {
		return moduleProvider.get().getTextMap().get("inemenu_settings");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().getTextMap().get("loading");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> m
	* <u><i>Magyarul:</i></u> p
	*/
	public static String minShort() {
		return moduleProvider.get().getTextMap().get("minShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} mins ago
	* <u><i>Magyarul:</i></u> {number} perce
	*/
	public static String minsAgo(String number) {
		return moduleProvider.get().getTextMap().get("minsAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one day ago
	* <u><i>Magyarul:</i></u> egy napja
	*/
	public static String oneDayAgo() {
		return moduleProvider.get().getTextMap().get("oneDayAgo");
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one hour ago
	* <u><i>Magyarul:</i></u> egy órája
	*/
	public static String oneHourAgo() {
		return moduleProvider.get().getTextMap().get("oneHourAgo");
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one min ago
	* <u><i>Magyarul:</i></u> egy perce
	*/
	public static String oneMinAgo() {
		return moduleProvider.get().getTextMap().get("oneMinAgo");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Connected
	* <u><i>Magyarul:</i></u> Csatlakozva
	*/
	public static String reconnected() {
		return moduleProvider.get().getTextMap().get("reconnected");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No network connection. We will try to reconnect in a few seconds.
	* <u><i>Magyarul:</i></u> Nincs kapcsolat. Néhány másodperc múlva újrapróbálkozunk.
	*/
	public static String reconnection() {
		return moduleProvider.get().getTextMap().get("reconnection");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error occurred during save: <br /> {error}
	* <u><i>Magyarul:</i></u> A mentés során az alábbi hiba történt: <br /> {error}
	*/
	public static String saveError(String error) {
		return moduleProvider.get().getTextMap().get("saveError").replace("{error}", error);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Save successful.
	* <u><i>Magyarul:</i></u> Sikeresen elmentve
	*/
	public static String saveSuccessful() {
		return moduleProvider.get().getTextMap().get("saveSuccessful");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unknown error occurred during save!
	* <u><i>Magyarul:</i></u> A mentés során ismeretlen hiba történt.
	*/
	public static String saveUnknownError() {
		return moduleProvider.get().getTextMap().get("saveUnknownError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saving of {savedThing} in progress
	* <u><i>Magyarul:</i></u> A(z) {savedThing} mentése folyamatban
	*/
	public static String savingInProgress(String savedThing) {
		return moduleProvider.get().getTextMap().get("savingInProgress").replace("{savedThing}", savedThing);
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} secs ago
	* <u><i>Magyarul:</i></u> {number} másodperce
	*/
	public static String secSAgo(String number) {
		return moduleProvider.get().getTextMap().get("secSAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> s
	* <u><i>Magyarul:</i></u> mp
	*/
	public static String secShort() {
		return moduleProvider.get().getTextMap().get("secShort");
	}
}
