package com.inepex.ineom.shared.dispatch;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IFConsts;

public enum ManipulationTypes implements IsSerializable  {
		CREATE_OR_EDIT_REQUEST,
		DELETE,
		UNDELETE,
		REFRESH;
		
		public static String getValuesAsString(){
			String s = "";
			for (ManipulationTypes item : values()){
				s += item.name() + IFConsts.enumValueSplitChar;
			}
			if (s.length() > 0) s = s.substring(0, s.length()-1);
			return s;
		}
}
