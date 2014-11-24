package com.inepex.ineFrame.server;

import java.text.DecimalFormat;

import com.inepex.ineFrame.shared.util.NumberUtil;

public class NumberUtilSrv implements NumberUtil {
	
	public static String groupThousandsFormat = "#,###";
	public static String defaultDoubleFormat = ",##0.######";
	public static String rawDoubleFormat = "0.######";

	@Override
	public String formatNumberGroupThousands(Number val){
		if(val==null)
			return "";
		
		return new DecimalFormat(groupThousandsFormat).format(val).replace('.', ',').replace(" ", "&nbsp;");		
	}
	
	@Override
	public String formatNumberToFractial(Number val) {
		if(val==null)
			return "";
		
		return new DecimalFormat(defaultDoubleFormat).format(val).replace('.', ',').replace(" ", "&nbsp;");
	}
	
	@Override
	public String csvNumberToMin2Fractial(Number val) {
		if(val==null)
			return "";
		
		return new DecimalFormat(rawDoubleFormat).format(val).replace('.', ',').replace(" ", "&nbsp;");
	}
	
	@Override
	public String formatNumberToFractial(Number val, int fractial) {
		if(val==null)
			return "";
		
		if(fractial<=1)
			return formatNumberGroupThousands(val);
		
		StringBuffer format = new StringBuffer(",##0.");
		
		for(int i=0; i<fractial; i++) {
			format.append('0');
		}
		
		return new DecimalFormat(format.toString()).format(val).replace('.', ',').replace(" ", "&nbsp;");
	}

	@Override
	public String csvFormatNumberToFractial(Double val, int fractial) {
		if(val==null)
			return "";
		
		if(fractial<1)
			return formatNumberGroupThousands(val).replace("&nbsp;", "");
		
		StringBuffer format = new StringBuffer("0.");
		
		for(int i=0; i<fractial; i++) {
			format.append('0');
		}
		
		return new DecimalFormat(format.toString()).format(val).replace('.', ',');
	}

	@Override
	public String toFract(Number val, int fractial) {
		return formatNumberToFractial(val, fractial);
	}

	@Override
	public String toFract(Number val) {
		return formatNumberToFractial(val);
	}
}
