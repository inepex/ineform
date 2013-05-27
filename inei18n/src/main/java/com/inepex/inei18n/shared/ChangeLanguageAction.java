package com.inepex.inei18n.shared;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.inei18n.client.I18nStore_Client;

/**
 * 
 * this action can be replaced with these client side code lines:
 * {@code 
 * 
 * Cookies.setCookie(I18nStore_Client.LANG_COOKIE_ID, newLangString, new Date(Long.MAX_VALUE));				
 * Window.Location.reload();
 *
 * }
 * 
 * see {@link I18nStore_Client}
 */
public class ChangeLanguageAction implements Action<SimpleResult> {
	
	private String requestedLanguage;

	public ChangeLanguageAction() {
	}
	
	public ChangeLanguageAction(String requestedLanguage) {
		this.requestedLanguage = requestedLanguage;
	}
	
	public String getRequestedLanguage() {
		return requestedLanguage;
	}
	
	public void setRequestedLanguage(String requestedLanguage) {
		this.requestedLanguage = requestedLanguage;
	}
}
