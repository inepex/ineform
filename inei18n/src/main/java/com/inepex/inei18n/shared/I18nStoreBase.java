package com.inepex.inei18n.shared;

import java.util.TreeMap;

public class I18nStoreBase {

    protected TreeMap<String, I18nModule> modulesByName = new TreeMap<String, I18nModule>();

    public void registerModule(I18nModule module) {
        modulesByName.put(module.getModuleName(), module);
    }

}
