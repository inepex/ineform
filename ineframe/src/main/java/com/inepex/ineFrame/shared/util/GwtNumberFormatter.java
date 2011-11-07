package com.inepex.ineFrame.shared.util;

import com.google.gwt.i18n.client.NumberFormat;

public class GwtNumberFormatter implements NumberFormatter {

	@Override
	public String format(String format, double number) {
		return NumberFormat.getFormat(format).format(number);
	}

	@Override
	public String format(String format, float number) {
		return NumberFormat.getFormat(format).format(number);
	}

}
