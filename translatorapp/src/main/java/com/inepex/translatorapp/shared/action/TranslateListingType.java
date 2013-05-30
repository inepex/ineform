package com.inepex.translatorapp.shared.action;

import com.inepex.ineom.shared.IFConsts;

public enum TranslateListingType {
	Outdated, Recent, All;
	
	public static String getValuesAsString(){
		//TODO i18n
		String s = "";
		for (TranslateListingType item : values()){
			s += item.toString()+ IFConsts.enumValueSplitChar;
		}
		if (s.length() > 0) s = s.substring(0, s.length()-1);
		return s;
	}
}