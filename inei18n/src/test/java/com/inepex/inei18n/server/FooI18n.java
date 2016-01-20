package com.inepex.inei18n.server;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class FooI18n extends I18nModule {

    private static final long serialVersionUID = 1L;

    public static final String MODULE_NAME = "FooI18n";

    static I18nModuleProvider<FooI18n> moduleProvider;

    public FooI18n() {
        super(MODULE_NAME);
    }

    public FooI18n(I18nModuleProvider<FooI18n> moduleProvider) {
        super(MODULE_NAME);
        FooI18n.moduleProvider = moduleProvider;
    }

    public static String getI18nText(String key) {
        return moduleProvider.get().getText(key);
    }

    @Override
    public I18nModuleProvider<?> getI18nProvider() {
        return moduleProvider;
    }

    /**
     * <u><i>Description:</i></u> Description for foo1 <br />
     * <u><i>In English:</i></u> foo1_EN <u><i>Magyarul:</i></u> foo1_HU
     */
    public static String foo1() {
        return moduleProvider.get().getText("foo1");
    }

    /**
     * <u><i>Description:</i></u> Description for foo2 <br />
     * <u><i>In English:</i></u> foo2_EN <u><i>Magyarul:</i></u> foo2_HU
     */
    public static String foo2() {
        return moduleProvider.get().getText("foo2");
    }
}
