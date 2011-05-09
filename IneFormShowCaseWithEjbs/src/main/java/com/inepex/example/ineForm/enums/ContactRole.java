package com.inepex.example.ineForm.enums;

import com.inepex.ineom.shared.kvo.IFConsts;

public class ContactRole {

	public final static String VIEWER = "VIEWER";
	public final static String MODIFIER = "MODIFIER";
	public final static String ADMIN = "ADMIN";
	
	public static String getValuesAsString(){
		String s = "";
		s += VIEWER + IFConsts.enumValueSplitChar;
		s += MODIFIER + IFConsts.enumValueSplitChar;
		s += ADMIN + IFConsts.enumValueSplitChar;
		if (s.length() > 0) s = s.substring(0, s.length()-1);
		return s;
	}
}
