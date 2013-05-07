package com.inepex.ineFrame.client.util;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.Singleton;
import com.inepex.ineFrame.shared.util.NumberFormatter;

@Singleton
public class GwtNumberFormatter implements NumberFormatter {

	@Override
	public String format(String format, double number) {
		return NumberFormat.getFormat(format).format(number);
	}

}
