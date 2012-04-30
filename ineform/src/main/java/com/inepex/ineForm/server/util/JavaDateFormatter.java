package com.inepex.ineForm.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.inei18n.shared.CurrentLang;

public class JavaDateFormatter implements DateFormatter {

	private final Provider<CurrentLang> currentLangProv;
	
	@Inject
	public JavaDateFormatter(Provider<CurrentLang> currentLangProv) {
		super();
		this.currentLangProv = currentLangProv;
	}

	private Locale getLocale(){
		return new Locale(currentLangProv.get().getCurrentLang());
	}
	
	@Override
	public String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern, getLocale()).format(date);
	}
	
	@Override
	public String format(String pattern, Long date) {
		return new SimpleDateFormat(pattern, getLocale()).format(new Date(date));
	}

}
