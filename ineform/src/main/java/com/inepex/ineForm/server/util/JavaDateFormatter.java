package com.inepex.ineForm.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.inepex.ineFrame.shared.util.DateFormatter;

public class JavaDateFormatter implements DateFormatter {

	@Override
	public String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	@Override
	public String format(String pattern, Long date) {
		return new SimpleDateFormat(pattern).format(new Date(date));
	}

}
