package com.inepex.inei18n.server;

import com.google.inject.servlet.SessionScoped;

/**
 * This class is used to provide session scoped language setting to {@link WebServerCurrentLang}
 * 
 * @author istvanszoboszlai
 *
 */
@SessionScoped
public class SessionScopedLang {
	public String lang = "en";
}
