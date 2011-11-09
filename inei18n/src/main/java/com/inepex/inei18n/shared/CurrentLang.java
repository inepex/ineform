package com.inepex.inei18n.shared;

/**
 * Should provide a context dependent <a href="http://ftp.ics.uci.edu/pub/ietf/http/related/iso639.txt">ISO language
 * code</a>
 * @author istvan
 *
 */
public interface CurrentLang {
	String getCurrentLang();
	void setSessionLang(String lang);
	
	void setLangOverride(String langOverride);
	void resetLangOverride();
}
