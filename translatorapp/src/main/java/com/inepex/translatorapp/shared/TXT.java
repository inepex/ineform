package com.inepex.translatorapp.shared;

import java.util.Arrays;
import java.util.List;

public class TXT {

	public static class Roles {
		public static final String developer = "developer";
		public static final String translator = "translator";
		
		public static List<String> all() {
			return Arrays.asList(developer, translator);
		}
	}
	
	
}
