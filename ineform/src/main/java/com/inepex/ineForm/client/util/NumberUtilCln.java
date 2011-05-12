package com.inepex.ineForm.client.util;

import com.google.gwt.i18n.client.NumberFormat;
import com.inepex.ineFrame.shared.util.NumberUtil;


public class NumberUtilCln implements NumberUtil {
	
	public static String groupThousandsFormat = "#,###";
	public static String defaultDoubleFormat = ",##0.######";
	public static String rawDoubleFormat = "0.######";
	
	@Override
	public String formatNumberGroupThousands(Number val){
		if(val==null)
			return "";
		
		return NumberFormat.getFormat(groupThousandsFormat).format(val).replace(",", "&nbsp;").replace('.', ',');		
	}
	
	@Override
	public String formatNumberToFractial(Number val) {
		if(val==null)
			return "";
		
		return NumberFormat.getFormat(defaultDoubleFormat).format(val).replace(",", "&nbsp;").replace('.', ',');
	}
	
	@Override
	public String formatNumberToFractial(Number val, int fractial) {
		if(val==null)
			return "";

		if(fractial<1)
			return formatNumberGroupThousands(val);
		
		String format = "#,##0.";
		
		for(int i=0; i<fractial; i++) {
			format+="0";
		}
		
		return NumberFormat.getFormat(format).format(val).replace(",", "&nbsp;").replace('.', ',');
	}

	@Override
	public String csvNumberToMin2Fractial(Number val) {
		if(val==null)
			return "";
		
		return NumberFormat.getFormat(rawDoubleFormat).format(val).replace('.', ',');
	}

	@Override
	public String csvFormatNumberToFractial(Double val, int fractial) {
		if(val==null)
			return "";

		if(fractial<1)
			return formatNumberGroupThousands(val).replace("&nbsp;", "");
		
		String format = "0.";
		
		for(int i=0; i<fractial; i++) {
			format+="0";
		}
		
		return NumberFormat.getFormat(format).format(val).replace('.', ',');
	}
}
