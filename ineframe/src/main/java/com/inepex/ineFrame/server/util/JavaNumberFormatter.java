package com.inepex.ineFrame.server.util;

import java.text.DecimalFormat;

import com.inepex.ineFrame.shared.util.NumberFormatter;

public class JavaNumberFormatter implements NumberFormatter {

	@Override
	public String format(String format, double number) {
		return new DecimalFormat(format).format(number);
	}

}
