package com.inepex.ineom.shared.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SharedUtil {
	
	public static final String ID_PART_SEPARATOR = ".";
	
	public static List<String> Li(String... strings) {
		return Arrays.asList(strings);
	}
	
	public static String Str(Collection<String> args) {
		StringBuffer sb= new StringBuffer();
		boolean first=true;
		for (String s : args) {
			if(!first) {
				sb.append(ID_PART_SEPARATOR);
			} else {
				first=false;
			}
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	public static String Str(String... args) {
		StringBuffer sb= new StringBuffer();
		boolean first=true;
		for (String s : args) {
			if(!first) {
				sb.append(ID_PART_SEPARATOR);
			} else {
				first=false;
			}
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	public static List<String> listFromDotSeparated(String string){
		return Arrays.asList(string.split("[" + ID_PART_SEPARATOR + "]"));
	}

	public static String deepestKey(String string) {
		if(!isMultilevelKey(string))
			return string;
		
		String[] splittedKey = string.split("[" + ID_PART_SEPARATOR + "]");
		return splittedKey[splittedKey.length-1];
	}
	
	public static boolean isMultilevelKey(String key) {
		return key.contains(ID_PART_SEPARATOR);
	}

	public static Set<String> Set(String... strings) {
		Set<String> set = new TreeSet<String>();
		if(strings!=null) {
			for(String s : strings) {
				set.add(s);
			}
		}
		
		return set;
	}
	
	public static String escapeHtmlSpaces(String input) {
		if(input==null)
			return null;
		
		if(input.length()==0)
			return input;
		
		StringBuffer sb = new StringBuffer();
		boolean inTag=false;
		
		for(int i=0; i<input.length(); i++) {
			char c=input.charAt(i);
			switch(c) {
			case '<':
				inTag=true;
				sb.append(c);
				break;
			case '>':
				inTag=false;
				sb.append(c);
				break;
			case '-':
				if(inTag)
					sb.append(c);
				else
					sb.append("&#8209;");
				break;
			case ' ':
				if(inTag)
					sb.append(c);
				else
					sb.append("&nbsp;");
				break;
			default:
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
}
