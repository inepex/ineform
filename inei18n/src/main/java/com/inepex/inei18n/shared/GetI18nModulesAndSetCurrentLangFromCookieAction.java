package com.inepex.inei18n.shared;

import java.util.Collection;

import net.customware.gwt.dispatch.shared.Action;

public class GetI18nModulesAndSetCurrentLangFromCookieAction
    implements
    Action<GetI18nModulesAndSetCurrentLangFromCookieResult> {

    private String requestedLang;
    private Collection<String> moduleNames;

    public GetI18nModulesAndSetCurrentLangFromCookieAction() {}

    public GetI18nModulesAndSetCurrentLangFromCookieAction(
        String requestedLang,
        Collection<String> moduleNames) {
        this.requestedLang = requestedLang;
        this.moduleNames = moduleNames;
    }

    public String getRequestedLang() {
        return requestedLang;
    }

    public void setRequestedLang(String requestedLang) {
        this.requestedLang = requestedLang;
    }

    public Collection<String> getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(Collection<String> moduleNames) {
        this.moduleNames = moduleNames;
    }
}
