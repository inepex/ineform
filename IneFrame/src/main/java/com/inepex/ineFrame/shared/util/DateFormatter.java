package com.inepex.ineFrame.shared.util;

import java.util.Date;

public interface DateFormatter {

	/**
	 * May cause problems with timezone. Use format(String, Long) instead. 
	 */
	@Deprecated
	public String format(String pattern, Date date);
	
	public String format(String pattern, Long date);
}
