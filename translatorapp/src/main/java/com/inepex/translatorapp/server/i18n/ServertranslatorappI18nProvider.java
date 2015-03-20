package com.inepex.translatorapp.server.i18n;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServertranslatorappI18nProvider extends ServerI18nProvider<translatorappI18n> {

	private static final long serialVersionUID = 1L;

	public ServertranslatorappI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<translatorappI18n> getModuleClass() {
		return translatorappI18n.class;
	}

	@Override
	public translatorappI18n getVirgineI18nModule() {
		translatorappI18n module = new translatorappI18n(this);
		initTexts(module);
		return module;
	}
	
	public void initTexts(translatorappI18n module){
		module.getTextMap().put("all", "All");
		module.getTextMap().put("appname", "Translator App");
		module.getTextMap().put("inactiveAccount", "Your account is currently inactive. Please ask the administrator for roles!");
		module.getTextMap().put("invalid", "Special parts of translated value (or original) are invalid");
		module.getTextMap().put("lang_id", "Id");
		module.getTextMap().put("lang_isoName", "Iso name");
		module.getTextMap().put("massUpload", "Mass upload");
		module.getTextMap().put("massUploadAlert", "Error happened or module hasn't got languages. Please check it.");
		module.getTextMap().put("modRow_engModTime", "English mod. time");
		module.getTextMap().put("modRow_engVal", "English value");
		module.getTextMap().put("moduleLangDelQuestion", "This module contains <b>{emptyCount}</b> empty strings and <b>{translatedCount}</b> translated strings for the selected language. Do you really want remove this language?");
		module.getTextMap().put("moduleLang_id", "Id");
		module.getTextMap().put("moduleLang_lang", "Lang");
		module.getTextMap().put("moduleLang_module", "Module");
		module.getTextMap().put("moduleListPage", "I18n modules");
		module.getTextMap().put("moduleListPage_changeLangCmd", "Change langs");
		module.getTextMap().put("moduleListTitle", "<br />Module can be deleted, only after you remove all its languages.<br /><br />");
		module.getTextMap().put("moduleRow_description", "Description");
		module.getTextMap().put("moduleRow_id", "Id");
		module.getTextMap().put("moduleRow_key", "Key");
		module.getTextMap().put("moduleRow_module", "Module");
		module.getTextMap().put("moduleRow_values", "Values");
		module.getTextMap().put("module_id", "Id");
		module.getTextMap().put("module_langs", "Langs");
		module.getTextMap().put("module_name", "Name");
		module.getTextMap().put("module_rows", "Rows");
		module.getTextMap().put("nonMatchingPws", "The password and this value are not the same!");
		module.getTextMap().put("outdated", "Need to be translated");
		module.getTextMap().put("pageNotFound", "Page not found");
		module.getTextMap().put("recent", "From last 7 days");
		module.getTextMap().put("regAnchor", "Click me for registration");
		module.getTextMap().put("regError", "Some error has occurred! Tray again later!");
		module.getTextMap().put("regPageTitle", "Registration");
		module.getTextMap().put("reg_email", "E-mail");
		module.getTextMap().put("reg_id", "Id");
		module.getTextMap().put("reg_password", "Password");
		module.getTextMap().put("reg_passwordAgain", "Password again");
		module.getTextMap().put("registeredEmail", "There is already an account for this e-mail address!");
		module.getTextMap().put("rowListPage", "Rows");
		module.getTextMap().put("rowListPage_magicFilter", "<b>Free text search:</b>");
		module.getTextMap().put("rowUpload_extraColumn", "extra column: '{name}';?;?");
		module.getTextMap().put("rowUpload_invalidLine", "The line number {number} is invalid.");
		module.getTextMap().put("rowUpload_notForEveryLang", "There aren't column for every languages in header");
		module.getTextMap().put("rowUpload_notInHeader", "There is not '{fieladName}' in the header row.");
		module.getTextMap().put("rowUpload_rowDuplication", "Maybe row duplication by upload.");
		module.getTextMap().put("rowUpload_wasTwice", "'{fieldName}' is in header was twice");
		module.getTextMap().put("showEditpopup", "Popup editor");
		module.getTextMap().put("succesfulRegistration", "Successful registration. Your account will be activated soon.");
		module.getTextMap().put("textBoxWithPopupLabel", "Popup editor");
		module.getTextMap().put("transPage_listmodeSelect", "<b>Show:</b>");
		module.getTextMap().put("transPage_moduleSelect", "<b>Of module:</b>");
		module.getTextMap().put("translateTableRow_description", "Description");
		module.getTextMap().put("translateTableRow_engModTime", "Eng. last mod.");
		module.getTextMap().put("translateTableRow_engVal", "In English");
		module.getTextMap().put("translateTableRow_id", "Id");
		module.getTextMap().put("translateTableRow_key", "Key");
		module.getTextMap().put("translateTableRow_translatedValue", "TranslatedValue");
		module.getTextMap().put("translatedValue_id", "Id");
		module.getTextMap().put("translatedValue_lang", "Language");
		module.getTextMap().put("translatedValue_lastModTime", "LastModTime");
		module.getTextMap().put("translatedValue_lastModUser", "LastModUser");
		module.getTextMap().put("translatedValue_row", "Row");
		module.getTextMap().put("translatedValue_value", "Translated value");
		module.getTextMap().put("translatorPage", "Translator page");
		module.getTextMap().put("upladRows", "Upload rows");
		module.getTextMap().put("upload_header", "<h3>Csv content order</h3>");
		module.getTextMap().put("upload_rows", "<h3>Rows</h3>");
		module.getTextMap().put("userLang_id", "Id");
		module.getTextMap().put("userLang_lang", "Lang");
		module.getTextMap().put("userLang_user", "User");
		module.getTextMap().put("userListPage", "User list");
		module.getTextMap().put("user_email", "E-mail");
		module.getTextMap().put("user_id", "Id");
		module.getTextMap().put("user_role", "Role");
		module.getTextMap().put("user_translates", "Translates");
		module.getTextMap().put("weakPassword", "Password should be at least 6 characters long. It should be contain both letters and digits!");
		module.getTextMap().put("welcomeText", "<h2>Welcome to our Translator application!</h2><br />If you have an account, just log in with the panel on right.<br /><br />If not, you should register: <b>click</b> the link below!<br /><br />");
	}
}
