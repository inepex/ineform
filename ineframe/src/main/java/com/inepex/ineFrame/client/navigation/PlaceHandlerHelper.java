package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.REDIRECT;

import java.util.HashMap;
import java.util.Map;

import com.inepex.ineom.shared.descriptor.Node;

public class PlaceHandlerHelper {
	
	public static String getPlacePart(String currentFullToken) {
		StringBuffer sb = new StringBuffer();
		boolean firstToken=true;
		
		for(String s : currentFullToken.split(Node.ID_SEPARATOR)) {
			String[] parts = s.split(regExp(PlaceHandler.QUESTION_MARK));
			if (parts.length > 0 ) {
				if(firstToken)
					firstToken=false;
				else
					sb.append(Node.ID_SEPARATOR);
				
				sb.append(parts[0]);
			}
		}
		
		return sb.toString();
	}

	public static Map<String, String> getUrlParameters(String currentFullToken) {
		Map<String, String> urlParams = new HashMap<String, String>();
		
		String[] placeParts = currentFullToken.split(Node.ID_SEPARATOR);
		for(String placePart: placeParts) {
			String[] parts = placePart.split(regExp(PlaceHandler.QUESTION_MARK));
			if (parts.length < 2)
				continue;
	
			// parameter "redirect" behaves another way!
			if (parts[1].indexOf(REDIRECT) == 0) {
				String redirectParam = parts[1].substring(REDIRECT.length() + 1);
				if (parts.length > 2)
					redirectParam = redirectParam + PlaceHandler.QUESTION_MARK + parts[2];
				urlParams.put(REDIRECT, redirectParam);
				return urlParams;
			}
	
			String params[] = parts[1].split(regExp(PlaceHandler.AND_SIGN));
			for (String string : params) {
				String[] keyValue = string.split(regExp(PlaceHandler.EQUALS_SIGN));
				if (keyValue.length == 2)
					urlParams.put(keyValue[0], keyValue[1]);
			}
		}
		
		return urlParams;
	}
	
	public static String regExp(String str) {
		return "[" + str + "]";
	}
}
