package com.inepex.translatorapp.server.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.shared.Consts;

public class TranslatorAppUtil {
	
	public static TranslatedValue findEngValFor(TranslatedValue val) {
		for(TranslatedValue tv : val.getRow().getValues()) {
			if(Consts.defaultLang.equals(tv.getLang().getIsoName()))
				return tv;
		}
		return null;
	}
	
	public static boolean isOutdated(TranslatedValue val, TranslatedValue engVal) {
		return engVal==null ? true 
				: (engVal.getLastModTime()>val.getLastModTime() 
						|| val.getValue()==null 
						|| "".equals(val.getValue()));
	}
	
	public static boolean isInvalid(TranslatedValue val, TranslatedValue engVal) {
		if(engVal!=null) {
			return 
					!TranslatorAppUtil.isWellFormatted(engVal.getValue()) ||
					!TranslatorAppUtil.isWellFormatted(val.getValue()) ||
					!TranslatorAppUtil.areParamsOfTranslatedValid(engVal.getValue(), val.getValue());
		}
		
		return true;
	}

	static boolean isWellFormatted(String string) {
		if(string==null || string.length()<1)
			return true;
		
		return string.indexOf("\"")==-1 
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
	
	static boolean areParamsOfTranslatedValid(String original, String translated) {
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
