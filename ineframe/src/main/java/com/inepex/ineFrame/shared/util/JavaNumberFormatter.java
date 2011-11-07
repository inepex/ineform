package com.inepex.ineFrame.shared.util;

import java.text.DecimalFormat;

public class JavaNumberFormatter implements NumberFormatter {

	@Override
	public String format(String format, double number) {
		return new DecimalFormat(format).format(number);
	}

	@Override
	public String format(String format, float number) {
		return new DecimalFormat(format).format(number);
	}

}
