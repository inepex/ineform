package com.inepex.translatorapp.shared;

import java.util.Arrays;
import java.util.List;

import com.inepex.ineFrame.shared.util.date.DateHelper;

public class Consts {

	public static class Roles {
		public static final String developer = "developer";
		public static final String translator = "translator";
		
		public static List<String> all() {
			return Arrays.asList(developer, translator);
		}
	}
	
	public static final long recentTimeRange = DateHelper.dayInMs*7;
	
}
