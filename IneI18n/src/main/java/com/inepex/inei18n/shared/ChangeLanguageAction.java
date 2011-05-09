package com.inepex.inei18n.shared;

import net.customware.gwt.dispatch.shared.Action;

public class ChangeLanguageAction implements Action<SimpleResult> {
	public String requestedLanguage;

	public ChangeLanguageAction() {
	}
	
	public ChangeLanguageAction(String requestedLanguage) {
		this.requestedLanguage = requestedLanguage;
	}
	
}
