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
	
	public static String getI18nText(String key){
		return moduleProvider.get().getText(key);
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Captcha:
	* <u><i>Magyarul:</i></u> Captcha:
	*/
	public static String CAPTCHA() {
		return moduleProvider.get().getText("CAPTCHA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Logging in...
	* <u><i>Magyarul:</i></u> Belépés...
	*/
	public static String LOGGINGIN() {
		return moduleProvider.get().getText("LOGGINGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Login
	* <u><i>Magyarul:</i></u> Belépés
	*/
	public static String LOGIN() {
		return moduleProvider.get().getText("LOGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Log out
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String LOGOUT() {
		return moduleProvider.get().getText("LOGOUT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String OK() {
		return moduleProvider.get().getText("OK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password:
	* <u><i>Magyarul:</i></u> Jelszó:
	*/
	public static String PASSWORD() {
		return moduleProvider.get().getText("PASSWORD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User name:
	* <u><i>Magyarul:</i></u> E-mail cím:
	*/
	public static String USERNAME() {
		return moduleProvider.get().getText("USERNAME");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Question
	* <u><i>Magyarul:</i></u> Kérdés
	*/
	public static String confirmDialogTitle() {
		return moduleProvider.get().getText("confirmDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> d
	* <u><i>Magyarul:</i></u> n
	*/
	public static String dayShort() {
		return moduleProvider.get().getText("dayShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} days ago
	* <u><i>Magyarul:</i></u> {number} napja
	*/
	public static String daysAgo(String number) {
		return moduleProvider.get().getText("daysAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cancel
	* <u><i>Magyarul:</i></u> Mégse
	*/
	public static String dialogCancelButton() {
		return moduleProvider.get().getText("dialogCancelButton");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String dialogOkButton() {
		return moduleProvider.get().getText("dialogOkButton");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> not implemented yet...
	* <u><i>Magyarul:</i></u> készülőben...
	*/
	public static String dummyPageText() {
		return moduleProvider.get().getText("dummyPageText");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error
	* <u><i>Magyarul:</i></u> Hiba
	*/
	public static String errorDialogTitle() {
		return moduleProvider.get().getText("errorDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!
	* <u><i>Magyarul:</i></u> A szerveren nem várt hiba lépett fel! Próbálja meg újratölteni az oldalt. Ha a hibajelenséget továbbra is tapasztalja kérem értesítse a rendszergazdát!
	*/
	public static String generalError() {
		return moduleProvider.get().getText("generalError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> h
	* <u><i>Magyarul:</i></u> ó
	*/
	public static String hourShort() {
		return moduleProvider.get().getText("hourShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} hours ago
	* <u><i>Magyarul:</i></u> {number} órája
	*/
	public static String hoursAgo(String number) {
		return moduleProvider.get().getText("hoursAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Apps
	* <u><i>Magyarul:</i></u> Appok
	*/
	public static String inemenu_apps() {
		return moduleProvider.get().getText("inemenu_apps");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help
	* <u><i>Magyarul:</i></u> Súgó
	*/
	public static String inemenu_help() {
		return moduleProvider.get().getText("inemenu_help");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help & Settings
	* <u><i>Magyarul:</i></u> Súgó, beállítások
	*/
	public static String inemenu_helpsettings() {
		return moduleProvider.get().getText("inemenu_helpsettings");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Leave alias
	* <u><i>Magyarul:</i></u> Alias mód elhagyása
	*/
	public static String inemenu_leavealias() {
		return moduleProvider.get().getText("inemenu_leavealias");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Logout
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String inemenu_logout() {
		return moduleProvider.get().getText("inemenu_logout");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Settings
	* <u><i>Magyarul:</i></u> Beállítások
	*/
	public static String inemenu_settings() {
		return moduleProvider.get().getText("inemenu_settings");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().getText("loading");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> m
	* <u><i>Magyarul:</i></u> p
	*/
	public static String minShort() {
		return moduleProvider.get().getText("minShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} mins ago
	* <u><i>Magyarul:</i></u> {number} perce
	*/
	public static String minsAgo(String number) {
		return moduleProvider.get().getText("minsAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one day ago
	* <u><i>Magyarul:</i></u> egy napja
	*/
	public static String oneDayAgo() {
		return moduleProvider.get().getText("oneDayAgo");
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one hour ago
	* <u><i>Magyarul:</i></u> egy órája
	*/
	public static String oneHourAgo() {
		return moduleProvider.get().getText("oneHourAgo");
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one min ago
	* <u><i>Magyarul:</i></u> egy perce
	*/
	public static String oneMinAgo() {
		return moduleProvider.get().getText("oneMinAgo");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Connected
	* <u><i>Magyarul:</i></u> Csatlakozva
	*/
	public static String reconnected() {
		return moduleProvider.get().getText("reconnected");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No network connection. We will try to reconnect in a few seconds.
	* <u><i>Magyarul:</i></u> Nincs kapcsolat. Néhány másodperc múlva újrapróbálkozunk.
	*/
	public static String reconnection() {
		return moduleProvider.get().getText("reconnection");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error occurred during save: <br /> {error}
	* <u><i>Magyarul:</i></u> A mentés során az alábbi hiba történt: <br /> {error}
	*/
	public static String saveError(String error) {
		return moduleProvider.get().getText("saveError").replace("{error}", error);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Save successful.
	* <u><i>Magyarul:</i></u> Sikeresen elmentve
	*/
	public static String saveSuccessful() {
		return moduleProvider.get().getText("saveSuccessful");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unknown error occurred during save!
	* <u><i>Magyarul:</i></u> A mentés során ismeretlen hiba történt.
	*/
	public static String saveUnknownError() {
		return moduleProvider.get().getText("saveUnknownError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saving of {savedThing} in progress
	* <u><i>Magyarul:</i></u> A(z) {savedThing} mentése folyamatban
	*/
	public static String savingInProgress(String savedThing) {
		return moduleProvider.get().getText("savingInProgress").replace("{savedThing}", savedThing);
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} secs ago
	* <u><i>Magyarul:</i></u> {number} másodperce
	*/
	public static String secSAgo(String number) {
		return moduleProvider.get().getText("secSAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> s
	* <u><i>Magyarul:</i></u> mp
	*/
	public static String secShort() {
		return moduleProvider.get().getText("secShort");
	}
}
