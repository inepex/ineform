package com.inepex.inei18n.shared;

/**
 * Should provide a context dependent <a
 * href="http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt">ISO language
 * code</a>
 * 
 * @author istvan
 *
 */
public abstract class CurrentLang {

    public static String DEFAULT_LANG = "en";

    public abstract String getCurrentLang();

    public abstract void setSessionLang(String lang);

    public abstract void setLangOverride(String langOverride);

    public abstract void resetLangOverride();
}
