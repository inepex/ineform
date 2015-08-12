package com.inepex.inei18n.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public abstract class ServerI18nProvider<T extends I18nModule> implements I18nModuleProvider<T> {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ServerI18nProvider.class);

    private final Map<String, T> i18nsByLang = new HashMap<String, T>();

    private Provider<CurrentLang> currentLangProvider;

    public ServerI18nProvider() {}

    public ServerI18nProvider(Provider<CurrentLang> currentLangProvider) {
        this.currentLangProvider = currentLangProvider;
    }

    @Override
    public T get() {
        return i18nsByLang.get(currentLangProvider.get().getCurrentLang());
    }

    @SuppressWarnings("unchecked")
    public void addI18nForLang(String lang, I18nModule i18n) {
        i18nsByLang.put(lang, (T) i18n);
    }

    public T getI18nForLang(String lang) {
        if (!i18nsByLang.containsKey(lang)) {
            i18nsByLang.put(lang, getVirgineI18nModule());
        }
        return i18nsByLang.get(lang);
    }

    public void setByKeyAndLang(String lang, String key, String value) {
        if (lang == null || key == null) {
            logger.warn(
                "setByKeyAndLang called with invalid properties lang: {}, key: {}, value: {}",
                new Object[] { lang, key, value });
            return;
        }
        T module = getI18nForLang(lang);
        module.getTextMap().put(key, value);
    }

    protected abstract Class<T> getModuleClass();

    public abstract T getVirgineI18nModule();

}
