package com.inepex.translatorapp.shared.action;

import com.inepex.ineom.shared.IFConsts;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public enum TranslateListingType {
    Outdated,
    Recent,
    All;

    public static String getValuesAsString() {
        return translatorappI18n.outdated()
            + IFConsts.enumValueSplitChar
            + translatorappI18n.recent()
            + IFConsts.enumValueSplitChar
            + translatorappI18n.all();
    }
}