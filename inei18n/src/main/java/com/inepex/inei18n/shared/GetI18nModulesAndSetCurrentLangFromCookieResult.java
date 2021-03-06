package com.inepex.inei18n.shared;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetI18nModulesAndSetCurrentLangFromCookieResult extends SimpleResult
        implements Serializable, IsSerializable {

    private static final long serialVersionUID = 1L;

    private String currentLang;
    private HashMap<String, I18nModule> i18nModulesByName;

    public GetI18nModulesAndSetCurrentLangFromCookieResult() {}

    public GetI18nModulesAndSetCurrentLangFromCookieResult(
        String currentLang,
        HashMap<String, I18nModule> i18nModulesByName) {
        this.currentLang = currentLang;
        this.i18nModulesByName = i18nModulesByName;
    }

    public String getCurrentLang() {
        return currentLang;
    }

    public void setCurrentLang(String currentLang) {
        this.currentLang = currentLang;
    }

    public HashMap<String, I18nModule> getI18nModulesByName() {
        return i18nModulesByName;
    }

    public void setI18nModulesByName(HashMap<String, I18nModule> i18nModulesByName) {
        this.i18nModulesByName = i18nModulesByName;
    }

}
