package com.inepex.inei18n.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.inei18n.shared.CurrentLang;

@Singleton
public class WebServerCurrentLang implements CurrentLang {
	
	final static Logger logger = LoggerFactory.getLogger(WebServerCurrentLang.class);
	
	private ThreadLocal<String> threadLangOverride = new ThreadLocal<String>();
	private final Provider<SessionScopedLang> sessionLangProvider;
	
	@Inject
	public WebServerCurrentLang(Provider<SessionScopedLang> sessionLangProvider) {
		this.sessionLangProvider = sessionLangProvider;
	}	

	@Override
	public String getCurrentLang() {
		String override = threadLangOverride.get();
		if (override != null)
			return override;
		
		try {
			return sessionLangProvider.get().lang;
		} catch (Exception e) {
			//TODO log invalid usage
//			logger.debug(e.getMessage(), e);
			return DEFAULT_LANG;
		}
	}
	
	@Override
	public void setSessionLang(String lang) {
		try {
			sessionLangProvider.get().lang = lang;
		} catch (Exception e) {
			logger.warn("Tried to set sessions current language out of session scope!");
		}
	}

	/**
	 * Overrides the current lang for the executing thread!
	 * @param langOverride
	 */
	public void setLangOverride(String langOverride) {
		this.threadLangOverride.set(langOverride);
	}

	/**
	 * Deletes the lang overrdie for the current thread
	 */
	public void resetLangOverride() {
		threadLangOverride.set(null);
	}
	

}
