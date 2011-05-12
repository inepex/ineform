package com.inepex.ineom.shared.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.validation.ValidationResult;

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
	
	public static void validateBoolean(String errorMsg, ValidationResult vr, AssistedObject kvo, String... fields) {
		for(String field : fields) {
			Boolean b = kvo.getBoolean(field);
			if(b!=null && b) return;
		}
		
		vr.addFieldError(fields[0], errorMsg);
	}
}
