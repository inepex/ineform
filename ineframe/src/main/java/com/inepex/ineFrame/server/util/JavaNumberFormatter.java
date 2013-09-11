package com.inepex.ineFrame.server.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.inepex.ineFrame.shared.util.NumberFormatter;

public class JavaNumberFormatter implements NumberFormatter {

	@Override
	public String format(String format, double number) {
		return new DecimalFormat(format, new DecimalFormatSymbols(Locale.ENGLISH)).format(number);
	}

}
