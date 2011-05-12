package com.inepex.ineFrame.shared.util;

public interface NumberUtil {

	public String formatNumberGroupThousands(Number val);
	public String formatNumberToFractial(Number val, int fractial);
	public String formatNumberToFractial(Number val);
	
	public String csvNumberToMin2Fractial(Number val);
	public String csvFormatNumberToFractial(Double val, int fractial);
	
}
