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

    public static String getI18nText(String key) {
        return moduleProvider.get().getText(key);
    }

    @Override
    public I18nModuleProvider<?> getI18nProvider() {
        return moduleProvider;
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> All <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String all() {
        return moduleProvider.get().getText("all");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Translator App <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String appname() {
        return moduleProvider.get().getText("appname");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Your account is currently inactive. Please ask
     * the administrator for roles! <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String inactiveAccount() {
        return moduleProvider.get().getText("inactiveAccount");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Special parts of translated value (or original)
     * are invalid <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String invalid() {
        return moduleProvider.get().getText("invalid");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String lang_id() {
        return moduleProvider.get().getText("lang_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Iso name <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String lang_isoName() {
        return moduleProvider.get().getText("lang_isoName");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Mass upload <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String massUpload() {
        return moduleProvider.get().getText("massUpload");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Error happened or module hasn't got languages.
     * Please check it. <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String massUploadAlert() {
        return moduleProvider.get().getText("massUploadAlert");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> English mod. time <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String modRow_engModTime() {
        return moduleProvider.get().getText("modRow_engModTime");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> English value <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String modRow_engVal() {
        return moduleProvider.get().getText("modRow_engVal");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> This module contains <b>{emptyCount}</b> empty
     * strings and <b>{translatedCount}</b> translated strings for the selected
     * language. Do you really want remove this language?
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String moduleLangDelQuestion(String emptyCount, String translatedCount) {
        return moduleProvider
            .get()
            .getText("moduleLangDelQuestion")
            .replace("{emptyCount}", emptyCount)
            .replace("{translatedCount}", translatedCount);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String moduleLang_id() {
        return moduleProvider.get().getText("moduleLang_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Lang <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleLang_lang() {
        return moduleProvider.get().getText("moduleLang_lang");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Module <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleLang_module() {
        return moduleProvider.get().getText("moduleLang_module");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> I18n modules <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleListPage() {
        return moduleProvider.get().getText("moduleListPage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Change langs <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleListPage_changeLangCmd() {
        return moduleProvider.get().getText("moduleListPage_changeLangCmd");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <br />
     * Module can be deleted, only after you remove all its languages.<br />
     * <br />
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String moduleListTitle() {
        return moduleProvider.get().getText("moduleListTitle");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Description <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleRow_description() {
        return moduleProvider.get().getText("moduleRow_description");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String moduleRow_id() {
        return moduleProvider.get().getText("moduleRow_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Key <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleRow_key() {
        return moduleProvider.get().getText("moduleRow_key");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Module <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleRow_module() {
        return moduleProvider.get().getText("moduleRow_module");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Values <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String moduleRow_values() {
        return moduleProvider.get().getText("moduleRow_values");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String module_id() {
        return moduleProvider.get().getText("module_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Langs <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String module_langs() {
        return moduleProvider.get().getText("module_langs");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Name <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String module_name() {
        return moduleProvider.get().getText("module_name");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Rows <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String module_rows() {
        return moduleProvider.get().getText("module_rows");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The password and this value are not the same!
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String nonMatchingPws() {
        return moduleProvider.get().getText("nonMatchingPws");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Need to be translated <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String outdated() {
        return moduleProvider.get().getText("outdated");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Page not found <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String pageNotFound() {
        return moduleProvider.get().getText("pageNotFound");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> From last 7 days <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String recent() {
        return moduleProvider.get().getText("recent");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Click me for registration
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String regAnchor() {
        return moduleProvider.get().getText("regAnchor");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Some error has occurred! Tray again later!
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String regError() {
        return moduleProvider.get().getText("regError");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Registration <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String regPageTitle() {
        return moduleProvider.get().getText("regPageTitle");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> E-mail <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String reg_email() {
        return moduleProvider.get().getText("reg_email");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String reg_id() {
        return moduleProvider.get().getText("reg_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Password <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String reg_password() {
        return moduleProvider.get().getText("reg_password");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Password again <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String reg_passwordAgain() {
        return moduleProvider.get().getText("reg_passwordAgain");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> There is already an account for this e-mail
     * address! <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String registeredEmail() {
        return moduleProvider.get().getText("registeredEmail");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Rows <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String rowListPage() {
        return moduleProvider.get().getText("rowListPage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <b>Free text search:</b>
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowListPage_magicFilter() {
        return moduleProvider.get().getText("rowListPage_magicFilter");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> extra column: '{name}';?;?
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowUpload_extraColumn(String name) {
        return moduleProvider.get().getText("rowUpload_extraColumn").replace("{name}", name);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The line number {number} is invalid.
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowUpload_invalidLine(String number) {
        return moduleProvider.get().getText("rowUpload_invalidLine").replace("{number}", number);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> There aren't column for every languages in
     * header <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowUpload_notForEveryLang() {
        return moduleProvider.get().getText("rowUpload_notForEveryLang");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> There is not '{fieladName}' in the header row.
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowUpload_notInHeader(String fieladName) {
        return moduleProvider
            .get()
            .getText("rowUpload_notInHeader")
            .replace("{fieladName}", fieladName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Maybe row duplication by upload.
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowUpload_rowDuplication() {
        return moduleProvider.get().getText("rowUpload_rowDuplication");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> '{fieldName}' is in header was twice
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String rowUpload_wasTwice(String fieldName) {
        return moduleProvider.get().getText("rowUpload_wasTwice").replace("{fieldName}", fieldName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Popup editor <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String showEditpopup() {
        return moduleProvider.get().getText("showEditpopup");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Successful registration. Your account will be
     * activated soon. <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String succesfulRegistration() {
        return moduleProvider.get().getText("succesfulRegistration");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Popup editor <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String textBoxWithPopupLabel() {
        return moduleProvider.get().getText("textBoxWithPopupLabel");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <b>Show:</b> <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String transPage_listmodeSelect() {
        return moduleProvider.get().getText("transPage_listmodeSelect");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <b>Of module:</b> <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String transPage_moduleSelect() {
        return moduleProvider.get().getText("transPage_moduleSelect");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Description <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translateTableRow_description() {
        return moduleProvider.get().getText("translateTableRow_description");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Eng. last mod. <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translateTableRow_engModTime() {
        return moduleProvider.get().getText("translateTableRow_engModTime");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> In English <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translateTableRow_engVal() {
        return moduleProvider.get().getText("translateTableRow_engVal");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String translateTableRow_id() {
        return moduleProvider.get().getText("translateTableRow_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Key <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translateTableRow_key() {
        return moduleProvider.get().getText("translateTableRow_key");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> TranslatedValue <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translateTableRow_translatedValue() {
        return moduleProvider.get().getText("translateTableRow_translatedValue");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String translatedValue_id() {
        return moduleProvider.get().getText("translatedValue_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Language <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translatedValue_lang() {
        return moduleProvider.get().getText("translatedValue_lang");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> LastModTime <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translatedValue_lastModTime() {
        return moduleProvider.get().getText("translatedValue_lastModTime");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> LastModUser <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translatedValue_lastModUser() {
        return moduleProvider.get().getText("translatedValue_lastModUser");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Row <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translatedValue_row() {
        return moduleProvider.get().getText("translatedValue_row");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Translated value <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translatedValue_value() {
        return moduleProvider.get().getText("translatedValue_value");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Translator page <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String translatorPage() {
        return moduleProvider.get().getText("translatorPage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Upload rows <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String upladRows() {
        return moduleProvider.get().getText("upladRows");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <h3>Csv content order</h3>
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String upload_header() {
        return moduleProvider.get().getText("upload_header");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <h3>Rows</h3> <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String upload_rows() {
        return moduleProvider.get().getText("upload_rows");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String userLang_id() {
        return moduleProvider.get().getText("userLang_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Lang <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String userLang_lang() {
        return moduleProvider.get().getText("userLang_lang");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> User <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String userLang_user() {
        return moduleProvider.get().getText("userLang_user");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> User list <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String userListPage() {
        return moduleProvider.get().getText("userListPage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> E-mail <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String user_email() {
        return moduleProvider.get().getText("user_email");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Id <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String user_id() {
        return moduleProvider.get().getText("user_id");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Role <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String user_role() {
        return moduleProvider.get().getText("user_role");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Translates <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String user_translates() {
        return moduleProvider.get().getText("user_translates");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Password should be at least 6 characters long.
     * It should be contain both letters and digits! <u><i>Magyarul:</i></u>
     * $loc.getString("hu")
     */
    public static String weakPassword() {
        return moduleProvider.get().getText("weakPassword");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> <h2>Welcome to our Translator application!</h2><br />
     * If you have an account, just log in with the panel on right.<br />
     * <br />
     * If not, you should register: <b>click</b> the link below!<br />
     * <br />
     * <u><i>Magyarul:</i></u> $loc.getString("hu")
     */
    public static String welcomeText() {
        return moduleProvider.get().getText("welcomeText");
    }
}
