package com.inepex.example.ineForm.enums;

import com.inepex.ineom.shared.IFConsts;

public enum ContactState {

	ACTIVE
	, INACTIVE
	, DELETED;
	
	public static String getValuesAsString(){
		String s = "";
		for (ContactState item : values()){
			s += item.name() + IFConsts.enumValueSplitChar;
		}
		if (s.length() > 0) s = s.substring(0, s.length()-1);
		return s;
	}
	
	public long getValueAsLong() {
		return (long)this.ordinal();
	}
}
