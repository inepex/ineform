package com.inepex.translatorapp.server.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class TranslatedUtil {

	public static boolean isWellFormatted(String string) {
		if(string==null || string.length()<1)
			return true;
		
		return string.indexOf("\"")==-1
				&& StringUtils.countMatches(string, "'")%2==0 
				&& hasValidBrackets(string, '<', '>')
				&& hasValidBrackets(string, '(', ')')
				&& hasValidBrackets(string, '[', ']')
				&& hasValidBrackets(string, '{', '}');
	}
	
	private static boolean hasValidBrackets(String string, char open, char close) {
		int opened=0;
		
		for(int i=0; i<string.length(); i++) {
			char c = string.charAt(i);
			if(c==open) {
				if(opened==0)
					opened++;
				else
					return false;
			} else if(c==close) {
				if(opened==1)
					opened--;
				else
					return false;
			}
		}
		
		return opened==0;
	}
	
	public static boolean areParamsOfTranslatedValid(String original, String translated) {
		if(!isWellFormatted(original) || !isWellFormatted(translated))
			throw new IllegalArgumentException();
		
		if(original==null || translated==null)
			return true;
		
		Map<String, Integer> origParams = analyze(original);
		Map<String, Integer> transParams = analyze(translated);
		
		if(origParams.size()!=transParams.size())
			return false;
		
		if(!origParams.keySet().containsAll(transParams.keySet()))
			return false;
		
		for(Entry<String, Integer> e : origParams.entrySet()) {
			if(!e.getValue().equals(transParams.get(e.getKey())))
				return false;
		}
		
		return true;
	}

	private static Map<String, Integer> analyze(String stringWithParams) {
		if(stringWithParams.indexOf("{")==-1)
			return Collections.emptyMap();
		
		HashMap<String, Integer> params = new HashMap<>();
		
		String tmp = stringWithParams.replaceFirst("[^\\}]*\\z", "");
		tmp=tmp.replaceAll("[^\\{\\}]*\\{", "");
		String[] paramNames = tmp.split("}");
		
		
		for(String p : paramNames) {
			Integer i=0;
			if(params.containsKey(p))
				i=params.get(p);
			
			i++;
			
			params.put(p, i);
		}
		
		return params;
	}

}
