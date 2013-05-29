package com.inepex.translatorapp.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class translatorappI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "translatorappI18n";
	
	static I18nModuleProvider<translatorappI18n> moduleProvider;
	
	public translatorappI18n() {
		super(MODULE_NAME);
	}
		
	public translatorappI18n(I18nModuleProvider<translatorappI18n> moduleProvider) {
		super(MODULE_NAME);
		translatorappI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public String inactiveAccount = "Your account is currently inactive. Please ask the administrator for roles!";
	public String lang_id = "Id";
	public String lang_isoName = "Iso name";
	public String nonMatchingPws = "The password and this value are not the same!";
	public String pageNotFound = "Page not found";
	public String regAnchor = "Click me for registration";
	public String regError = "Some error has occurred! Tray again later!";
	public String regPageTitle = "Registration";
	public String reg_email = "E-mail";
	public String reg_id = "Id";
	public String reg_password = "Password";
	public String reg_passwordAgain = "Password again";
	public String registeredEmail = "There is already an account for this e-mail address!";
	public String succesfulRegistration = "Successful registration. Your account will be activated soon.";
	public String translatorPage = "Translator page";
	public String userListPage = "User list";
	public String userListTitle = "<h2>Current users</h2><br/>";
	public String user_email = "E-mail";
	public String user_id = "Id";
	public String user_role = "Role";
	public String user_translates = "Translates";
	public String weakPassword = "Password should be at least 6 characters long. It should be contain both letters and digits!";
	public String welcomeText = "<h2>Welcome to our Translator application!</h2><br>If you have an account, just log in with the panel on right.<br><br>If not, you should register: <b>click</b> the link below!<br><br>";

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Your account is currently inactive. Please ask the administrator for roles!
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String inactiveAccount() {
		return moduleProvider.get().inactiveAccount;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String lang_id() {
		return moduleProvider.get().lang_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Iso name
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String lang_isoName() {
		return moduleProvider.get().lang_isoName;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The password and this value are not the same!
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String nonMatchingPws() {
		return moduleProvider.get().nonMatchingPws;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page not found
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String pageNotFound() {
		return moduleProvider.get().pageNotFound;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Click me for registration
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String regAnchor() {
		return moduleProvider.get().regAnchor;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Some error has occurred! Tray again later!
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String regError() {
		return moduleProvider.get().regError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Registration
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String regPageTitle() {
		return moduleProvider.get().regPageTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String reg_email() {
		return moduleProvider.get().reg_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String reg_id() {
		return moduleProvider.get().reg_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String reg_password() {
		return moduleProvider.get().reg_password;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password again
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String reg_passwordAgain() {
		return moduleProvider.get().reg_passwordAgain;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> There is already an account for this e-mail address!
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String registeredEmail() {
		return moduleProvider.get().registeredEmail;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Successful registration. Your account will be activated soon.
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String succesfulRegistration() {
		return moduleProvider.get().succesfulRegistration;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Translator page
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatorPage() {
		return moduleProvider.get().translatorPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User list
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String userListPage() {
		return moduleProvider.get().userListPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <h2>Current users</h2><br/>
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String userListTitle() {
		return moduleProvider.get().userListTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String user_email() {
		return moduleProvider.get().user_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String user_id() {
		return moduleProvider.get().user_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Role
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String user_role() {
		return moduleProvider.get().user_role;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Translates
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String user_translates() {
		return moduleProvider.get().user_translates;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password should be at least 6 characters long. It should be contain both letters and digits!
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String weakPassword() {
		return moduleProvider.get().weakPassword;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <h2>Welcome to our Translator application!</h2><br>If you have an account, just log in with the panel on right.<br><br>If not, you should register: <b>click</b> the link below!<br><br>
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String welcomeText() {
		return moduleProvider.get().welcomeText;
	}
}
