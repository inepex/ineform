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
	
	public String all = "All";
	public String appname = "Translator Application";
	public String doneBtn = "Done";
	public String inactiveAccount = "Your account is currently inactive. Please ask the administrator for roles!";
	public String invalid = "Special parts of translated value (or original) are invalid";
	public String lang_id = "Id";
	public String lang_isoName = "Iso name";
	public String modRow_engModTime = "English mod. time";
	public String modRow_engVal = "English value";
	public String moduleLangDelQuestion = "This module contains <b>{emptyCount}</b> empty strings and <b>{translatedCount}</b> translated strings for the selected language. Do you really want remove this language?";
	public String moduleLang_id = "Id";
	public String moduleLang_lang = "Lang";
	public String moduleLang_module = "Module";
	public String moduleListPage = "I18n modules";
	public String moduleListPage_changeLangCmd = "Change langs";
	public String moduleListTitle = "<br />Module can be deleted, only after you remove all its languages.<br /><br />";
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
	public String outdated = "Need to be translated";
	public String pageNotFound = "Page not found";
	public String recent = "From last 7 days";
	public String regAnchor = "Click me for registration";
	public String regError = "Some error has occurred! Tray again later!";
	public String regPageTitle = "Registration";
	public String reg_email = "E-mail";
	public String reg_id = "Id";
	public String reg_password = "Password";
	public String reg_passwordAgain = "Password again";
	public String registeredEmail = "There is already an account for this e-mail address!";
	public String rowListPage = "Rows";
	public String rowListPage_magicFilter = "<b>Free text search:</b>";
	public String rowUpload_extraColumn = "extra column: '{name}';?;?";
	public String rowUpload_invalidLine = "The line number {number} is invalid.";
	public String rowUpload_notForEveryLang = "There aren't column for every languages in header";
	public String rowUpload_notInHeader = "There is not '{fieladName}' in the header row.";
	public String rowUpload_rowDuplication = "Maybe row duplication by upload.";
	public String rowUpload_wasTwice = "'{fieldName}' is in header was twice";
	public String showEditpopup = "Popup editor";
	public String succesfulRegistration = "Successful registration. Your account will be activated soon.";
	public String transPage_listmodeSelect = "<b>Show:</b>";
	public String transPage_moduleSelect = "<b>Of module:</b>";
	public String translateTableRow_description = "Description";
	public String translateTableRow_engVal = "In English";
	public String translateTableRow_id = "Id";
	public String translateTableRow_translatedValue = "TranslatedValue";
	public String translatedValue_id = "Id";
	public String translatedValue_lang = "Language";
	public String translatedValue_lastModTime = "LastModTime";
	public String translatedValue_lastModUser = "LastModUser";
	public String translatedValue_row = "Row";
	public String translatedValue_value = "Translated value";
	public String translatorPage = "Translator page";
	public String upladRows = "Upload rows";
	public String upload_header = "<h3>Csv content order</h3>";
	public String upload_rows = "<h3>Rows</h3>";
	public String userLang_id = "Id";
	public String userLang_lang = "Lang";
	public String userLang_user = "User";
	public String userListPage = "User list";
	public String user_email = "E-mail";
	public String user_id = "Id";
	public String user_role = "Role";
	public String user_translates = "Translates";
	public String weakPassword = "Password should be at least 6 characters long. It should be contain both letters and digits!";
	public String welcomeText = "<h2>Welcome to our Translator application!</h2><br />If you have an account, just log in with the panel on right.<br /><br />If not, you should register: <b>click</b> the link below!<br /><br />";

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> All
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String all() {
		return moduleProvider.get().all;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Translator Application
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String appname() {
		return moduleProvider.get().appname;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Done
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String doneBtn() {
		return moduleProvider.get().doneBtn;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Your account is currently inactive. Please ask the administrator for roles!
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String inactiveAccount() {
		return moduleProvider.get().inactiveAccount;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Special parts of translated value (or original) are invalid
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String invalid() {
		return moduleProvider.get().invalid;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String lang_id() {
		return moduleProvider.get().lang_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Iso name
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String lang_isoName() {
		return moduleProvider.get().lang_isoName;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> English mod. time
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String modRow_engModTime() {
		return moduleProvider.get().modRow_engModTime;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> English value
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String modRow_engVal() {
		return moduleProvider.get().modRow_engVal;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This module contains <b>{emptyCount}</b> empty strings and <b>{translatedCount}</b> translated strings for the selected language. Do you really want remove this language?
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleLangDelQuestion(String translatedCount, String emptyCount) {
		return moduleProvider.get().moduleLangDelQuestion.replace("{translatedCount}", translatedCount).replace("{emptyCount}", emptyCount);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleLang_id() {
		return moduleProvider.get().moduleLang_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lang
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleLang_lang() {
		return moduleProvider.get().moduleLang_lang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Module
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleLang_module() {
		return moduleProvider.get().moduleLang_module;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> I18n modules
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleListPage() {
		return moduleProvider.get().moduleListPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Change langs
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleListPage_changeLangCmd() {
		return moduleProvider.get().moduleListPage_changeLangCmd;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <br />Module can be deleted, only after you remove all its languages.<br /><br />
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleListTitle() {
		return moduleProvider.get().moduleListTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Description
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleRow_description() {
		return moduleProvider.get().moduleRow_description;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleRow_id() {
		return moduleProvider.get().moduleRow_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Key
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleRow_key() {
		return moduleProvider.get().moduleRow_key;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Module
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleRow_module() {
		return moduleProvider.get().moduleRow_module;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Values
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String moduleRow_values() {
		return moduleProvider.get().moduleRow_values;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String module_id() {
		return moduleProvider.get().module_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Langs
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String module_langs() {
		return moduleProvider.get().module_langs;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String module_name() {
		return moduleProvider.get().module_name;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Rows
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String module_rows() {
		return moduleProvider.get().module_rows;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The password and this value are not the same!
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String nonMatchingPws() {
		return moduleProvider.get().nonMatchingPws;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Need to be translated
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String outdated() {
		return moduleProvider.get().outdated;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page not found
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String pageNotFound() {
		return moduleProvider.get().pageNotFound;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> From last 7 days
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String recent() {
		return moduleProvider.get().recent;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Click me for registration
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String regAnchor() {
		return moduleProvider.get().regAnchor;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Some error has occurred! Tray again later!
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String regError() {
		return moduleProvider.get().regError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Registration
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String regPageTitle() {
		return moduleProvider.get().regPageTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String reg_email() {
		return moduleProvider.get().reg_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String reg_id() {
		return moduleProvider.get().reg_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String reg_password() {
		return moduleProvider.get().reg_password;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password again
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String reg_passwordAgain() {
		return moduleProvider.get().reg_passwordAgain;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> There is already an account for this e-mail address!
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String registeredEmail() {
		return moduleProvider.get().registeredEmail;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Rows
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowListPage() {
		return moduleProvider.get().rowListPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <b>Free text search:</b>
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowListPage_magicFilter() {
		return moduleProvider.get().rowListPage_magicFilter;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> extra column: '{name}';?;?
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowUpload_extraColumn(String name) {
		return moduleProvider.get().rowUpload_extraColumn.replace("{name}", name);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The line number {number} is invalid.
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowUpload_invalidLine(String number) {
		return moduleProvider.get().rowUpload_invalidLine.replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> There aren't column for every languages in header
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowUpload_notForEveryLang() {
		return moduleProvider.get().rowUpload_notForEveryLang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> There is not '{fieladName}' in the header row.
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowUpload_notInHeader(String fieladName) {
		return moduleProvider.get().rowUpload_notInHeader.replace("{fieladName}", fieladName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Maybe row duplication by upload.
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowUpload_rowDuplication() {
		return moduleProvider.get().rowUpload_rowDuplication;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> '{fieldName}' is in header was twice
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String rowUpload_wasTwice(String fieldName) {
		return moduleProvider.get().rowUpload_wasTwice.replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Popup editor
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String showEditpopup() {
		return moduleProvider.get().showEditpopup;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Successful registration. Your account will be activated soon.
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String succesfulRegistration() {
		return moduleProvider.get().succesfulRegistration;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <b>Show:</b>
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String transPage_listmodeSelect() {
		return moduleProvider.get().transPage_listmodeSelect;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <b>Of module:</b>
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String transPage_moduleSelect() {
		return moduleProvider.get().transPage_moduleSelect;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Description
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translateTableRow_description() {
		return moduleProvider.get().translateTableRow_description;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> In English
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translateTableRow_engVal() {
		return moduleProvider.get().translateTableRow_engVal;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translateTableRow_id() {
		return moduleProvider.get().translateTableRow_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> TranslatedValue
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translateTableRow_translatedValue() {
		return moduleProvider.get().translateTableRow_translatedValue;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatedValue_id() {
		return moduleProvider.get().translatedValue_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Language
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatedValue_lang() {
		return moduleProvider.get().translatedValue_lang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> LastModTime
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatedValue_lastModTime() {
		return moduleProvider.get().translatedValue_lastModTime;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> LastModUser
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatedValue_lastModUser() {
		return moduleProvider.get().translatedValue_lastModUser;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Row
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatedValue_row() {
		return moduleProvider.get().translatedValue_row;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Translated value
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatedValue_value() {
		return moduleProvider.get().translatedValue_value;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Translator page
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String translatorPage() {
		return moduleProvider.get().translatorPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload rows
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String upladRows() {
		return moduleProvider.get().upladRows;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <h3>Csv content order</h3>
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String upload_header() {
		return moduleProvider.get().upload_header;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <h3>Rows</h3>
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String upload_rows() {
		return moduleProvider.get().upload_rows;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String userLang_id() {
		return moduleProvider.get().userLang_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lang
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String userLang_lang() {
		return moduleProvider.get().userLang_lang;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String userLang_user() {
		return moduleProvider.get().userLang_user;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User list
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String userListPage() {
		return moduleProvider.get().userListPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String user_email() {
		return moduleProvider.get().user_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String user_id() {
		return moduleProvider.get().user_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Role
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String user_role() {
		return moduleProvider.get().user_role;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Translates
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String user_translates() {
		return moduleProvider.get().user_translates;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password should be at least 6 characters long. It should be contain both letters and digits!
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String weakPassword() {
		return moduleProvider.get().weakPassword;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> <h2>Welcome to our Translator application!</h2><br />If you have an account, just log in with the panel on right.<br /><br />If not, you should register: <b>click</b> the link below!<br /><br />
	* <u><i>Magyarul:</i></u> $loc.getString("hu")
	*/
	public static String welcomeText() {
		return moduleProvider.get().welcomeText;
	}
}
