package com.inepex.inei18n.client;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.user.client.Cookies;
import com.google.inject.Singleton;
import com.inepex.inei18n.shared.ClientI18nProvider;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieResult;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nStoreBase;

@Singleton
public class I18nStore_Client extends I18nStoreBase {

    public final static String LANG_COOKIE_ID = "I18nStore_Client_last_used_lang";
    private String currentLanguage = "";

    public GetI18nModulesAndSetCurrentLangFromCookieAction getModuleQueryAction(
        boolean loadLangFromCookie) {
        return getModuleQueryAction(loadLangFromCookie, null);
    }

    /**
     * loads last used lang from cookie, creates a
     * GetI18nModulesAndSetCurrentLangFromCookieAction from registered modules
     * use forcedLanguage to override cookie lang. If forcedLang is null, lang
     * from cookie is used
     */
    public GetI18nModulesAndSetCurrentLangFromCookieAction getModuleQueryAction(
        boolean loadLangFromCookie,
        String forcedLanguage) {
        Collection<String> moduleNames = new ArrayList<String>();
        for (String string : this.modulesByName.keySet()) {
            moduleNames.add(string);
        }

        String requestedLang = null;
        if (forcedLanguage != null) {
            requestedLang = forcedLanguage;
        } else if (loadLangFromCookie) {
            requestedLang = Cookies.getCookie(LANG_COOKIE_ID);

            if (requestedLang == null) {
                requestedLang = getBrowserLang();
            }
        }

        return new GetI18nModulesAndSetCurrentLangFromCookieAction(requestedLang, moduleNames);
    }

    public void onModulesQueriedSuccess(GetI18nModulesAndSetCurrentLangFromCookieResult result) {
        currentLanguage = result.getCurrentLang();

        for (I18nModule i18nModule : modulesByName.values()) {
            ClientI18nProvider<?> clientProvider = (ClientI18nProvider<?>) i18nModule
                .getI18nProvider();
            clientProvider
                .setCurrentModule(result.getI18nModulesByName().get(i18nModule.getModuleName()));
        }
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    private String getBrowserLang() {
        String browserLang = getLanguage();
        if (browserLang == null || browserLang.length() < 1)
            return null;

        if (browserLang.startsWith("en"))
            return "en";

        return browserLang;
    }

    /**
     * Get preferred language according to browser settings.
     *
     * @return The preferred language.
     */
    public static native String getLanguage() /*-{
                                              if (navigator.language == null) {
                                              if (navigator.userLanguage == null) {
                                              return null;
                                              } else {
                                              return navigator.userLanguage;
                                              }
                                              } else {
                                              return navigator.language;
                                              }
                                              }-*/;
}
