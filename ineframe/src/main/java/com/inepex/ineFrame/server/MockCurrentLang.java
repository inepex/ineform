package com.inepex.ineFrame.server;

import com.inepex.inei18n.shared.CurrentLang;


public class MockCurrentLang implements CurrentLang {
	String overriddenLang = null;
	@Override
	public void setLangOverride(String langOverride) {
		this.overriddenLang = langOverride;
	}
	
	@Override
	public String getCurrentLang() {
		if (overriddenLang != null)
			return overriddenLang;
		return "en";
	}

	@Override
	public void resetLangOverride() {
		overriddenLang = null;
	}

	@Override
	public void setSessionLang(String lang) {
		throw new RuntimeException("Unimplemented yet");
	}		
}