package com.inepex.inei18n.server;

import com.google.inject.servlet.SessionScoped;
import com.inepex.inei18n.shared.CurrentLang;

/**
 * This class is used to provide session scoped language setting to
 * {@link WebServerCurrentLang}
 * 
 * @author istvanszoboszlai
 *
 */
@SessionScoped
public class SessionScopedLang {
    public String lang = CurrentLang.DEFAULT_LANG;
}
