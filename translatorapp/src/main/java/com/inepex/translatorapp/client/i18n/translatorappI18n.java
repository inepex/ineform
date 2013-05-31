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
	
	public String doneBtn = "Done";
	public String inactiveAccount = "Your account is currently inactive. Please ask the administrator for roles!";
	public String lang_id = "Id";
	public String lang_isoName = "Iso name";
	public String moduleLang_id = "Id";
	public String moduleLang_lang = "Lang";
	public String moduleLang_module = "Module";
	public String moduleListPage = "I18n modules";
	public String moduleListTitle = "<h2>Modules</h2>Module can be deleted, only after you remove all its languages.<br><br>";
	public String moduleRow_description = "Description";
	public String moduleRow_id = "Id";
	public String moduleRow_key = "Key";
	public String moduleRow_module = "Module";
	public String moduleRow_values = "Values";
	public String module_id = "Id";
	public String module_langs = "Langs";
	public String module_name = "Name";
	public String module_rows = "Rows";
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
	public String revertBtn = "Revert";
	public String showEditpopup = "Popup editor";
	public String succesfulRegistration = "Successful registration. Your account will be activated soon.";
	public String translateTableRow_description = "Description";
	public String translateTableRow_engVal = "EngVal";
	public String translateTableRow_id = "Id";
	public String translateTableRow_outDated = "OutDated";
	public String translateTableRow_recent = "Recent";
	public String translateTableRow_translatedValue = "TranslatedValue";
	public String translatedValue_id = "Id";
	public String translatedValue_lang = "Lang";
	public String translatedValue_lastModTime = "LastModTime";
	public String translatedValue_lastModUser = "LastModUser";
	public String translatedValue_row = "Row";
	public String translatedValue_value = "Value";
	public String translatorPage = "Translator page";
	public String userLang_id = "Id";
	public String userLang_lang = "Lang";
	public String userLang_user = "User";
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
	* <u><i>In English:</i></u> Done
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String doneBtn() {
		return moduleProvider.get().doneBtn;
	}
	
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
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleLang_id() {
		return moduleProvider.get().moduleLang_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lang
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleLang_lang() {
		return moduleProvider.get().moduleLang_lang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Module
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleLang_module() {
		return moduleProvider.get().moduleLang_module;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> I18n modules
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleListPage() {
		return moduleProvider.get().moduleListPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <h2>Modules</h2>Module can be deleted, only after you remove all its languages.<br><br>
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleListTitle() {
		return moduleProvider.get().moduleListTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Description
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleRow_description() {
		return moduleProvider.get().moduleRow_description;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleRow_id() {
		return moduleProvider.get().moduleRow_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Key
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleRow_key() {
		return moduleProvider.get().moduleRow_key;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Module
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleRow_module() {
		return moduleProvider.get().moduleRow_module;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Values
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String moduleRow_values() {
		return moduleProvider.get().moduleRow_values;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String module_id() {
		return moduleProvider.get().module_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Langs
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String module_langs() {
		return moduleProvider.get().module_langs;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String module_name() {
		return moduleProvider.get().module_name;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Rows
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String module_rows() {
		return moduleProvider.get().module_rows;
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
	* <u><i>In English:</i></u> Revert
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String revertBtn() {
		return moduleProvider.get().revertBtn;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Popup editor
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String showEditpopup() {
		return moduleProvider.get().showEditpopup;
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
	* <u><i>In English:</i></u> Description
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translateTableRow_description() {
		return moduleProvider.get().translateTableRow_description;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> EngVal
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translateTableRow_engVal() {
		return moduleProvider.get().translateTableRow_engVal;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translateTableRow_id() {
		return moduleProvider.get().translateTableRow_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> OutDated
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translateTableRow_outDated() {
		return moduleProvider.get().translateTableRow_outDated;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Recent
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translateTableRow_recent() {
		return moduleProvider.get().translateTableRow_recent;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> TranslatedValue
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translateTableRow_translatedValue() {
		return moduleProvider.get().translateTableRow_translatedValue;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatedValue_id() {
		return moduleProvider.get().translatedValue_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lang
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatedValue_lang() {
		return moduleProvider.get().translatedValue_lang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> LastModTime
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatedValue_lastModTime() {
		return moduleProvider.get().translatedValue_lastModTime;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> LastModUser
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatedValue_lastModUser() {
		return moduleProvider.get().translatedValue_lastModUser;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Row
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatedValue_row() {
		return moduleProvider.get().translatedValue_row;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Value
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String translatedValue_value() {
		return moduleProvider.get().translatedValue_value;
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
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String userLang_id() {
		return moduleProvider.get().userLang_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lang
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String userLang_lang() {
		return moduleProvider.get().userLang_lang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User
	* <u><i>Magyarul:</i></u> ?
	*/
	public static String userLang_user() {
		return moduleProvider.get().userLang_user;
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
