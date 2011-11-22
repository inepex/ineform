package com.inepex.ineForm.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.DateProvider;

@Singleton
public class GwtDateFormatter implements DateFormatter {

	DateProvider dateProvider;
	
	@Inject
	public GwtDateFormatter(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
	
	@Override
	public String format(String pattern, Date date) {
		return DateTimeFormat.getFormat(pattern).format(date);
	}

	@Override
	public String format(String pattern, Long dateInUtc) {
		Date date = dateProvider.getDate(dateInUtc);
		return DateTimeFormat.getFormat(pattern).format(date);
	}

}
