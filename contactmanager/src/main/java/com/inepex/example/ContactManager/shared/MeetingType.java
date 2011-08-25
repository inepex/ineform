package com.inepex.example.ContactManager.shared;

import com.inepex.example.ContactManager.client.i18n.CMI18nExt;
import com.inepex.ineom.shared.IFConsts;

public enum MeetingType {
	INE_OFFICE,
	PARNER_OFFICE,
	VIDEO_CONFERENCE,
	TELE_CONFERENCE;
	
	public static String getValuesAsString(){
		String s = "";
		for (MeetingType item : values()){
			s += CMI18nExt.getName(item)+ IFConsts.enumValueSplitChar;
		}
		if (s.length() > 0) s = s.substring(0, s.length()-1);
		return s;
	}
	
	public long getValueAsLong() {
		return (long)this.ordinal();
	}
}
