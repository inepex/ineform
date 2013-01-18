package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.REDIRECT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.ineom.shared.descriptor.Node;

public class PlaceHandlerHelper {
	
	public static final String listParamSeparator = ",";
	
	public static String regExp(String str) {
		//TODO: escape special characters like \
		return "[" + str + "]";
	}
	
	public static List<String> parseListParams(Map<String, String> urlParams, String key) {
		String names = urlParams.get(key);
		if(names==null || names.length()==0)
			return null;
		
		List<String> nameList = new ArrayList<String>();
		String[] nameArray = names.split(listParamSeparator);
		for(String name : nameArray){
			nameList.add(name);
		}
		return nameList;
	}
	
	public static String appendParam(String hierarchicalToken, String paramToken, List<String> listValue) {
		String value;
		if(listValue==null || listValue.isEmpty())
			value="";
		else {
			StringBuffer valSb = new StringBuffer();
			for(String name : listValue){
				valSb.append(name);
				valSb.append(listParamSeparator);
			}
			
			valSb.deleteCharAt(valSb.length()-1);
			value=valSb.toString();
		}
		
		return appendParam(hierarchicalToken, paramToken, value);
	}
	
	public static String appendParam(String hierarchicalToken, String paramToken, String value) {
		if(hierarchicalToken.length()<1)
			return "";

		String[] parts = hierarchicalToken.split(regExp(Node.ID_SEPARATOR));

		for(String part : parts){
			if(PlaceHandlerHelper.getUrlParameters(part).containsKey(paramToken)) {
				StringBuffer editedLastPart = new StringBuffer();
				int paramIndex = 
						part.indexOf(PlaceHandler.QUESTION_MARK+paramToken+PlaceHandler.EQUALS_SIGN);
				if(paramIndex<0)
					paramIndex=part.indexOf(PlaceHandler.AND_SIGN+paramToken+PlaceHandler.EQUALS_SIGN);
				
				//because of & or ?
				paramIndex++;
				
				editedLastPart.append(part.substring(0, paramIndex));
				editedLastPart.append(paramToken);
				editedLastPart.append(PlaceHandler.EQUALS_SIGN);
				editedLastPart.append(value);
				
				int at = part.indexOf(PlaceHandler.AND_SIGN, paramIndex);
				if(at>0)
					editedLastPart.append(part.substring(at, part.length()));
				
				return hierarchicalToken.replace(part, editedLastPart.toString());
			}
		}
		String lastPart = parts[parts.length - 1];
		if(lastPart.indexOf(PlaceHandler.QUESTION_MARK)>=0)
			return hierarchicalToken+PlaceHandler.AND_SIGN+paramToken+PlaceHandler.EQUALS_SIGN+value;
		else
			return hierarchicalToken+PlaceHandler.QUESTION_MARK+paramToken+PlaceHandler.EQUALS_SIGN+value;
	}
	
	public static String removeParam(String hierarchicalToken, String paramName) {
		int paramStart=hierarchicalToken.indexOf(PlaceHandler.AND_SIGN+paramName+PlaceHandler.EQUALS_SIGN);
		
		//not first param somewhere
		if(paramStart!=-1) {
			int nextControl=nextControl(hierarchicalToken, paramStart);
			if(nextControl==-1)
				return hierarchicalToken.substring(0, paramStart);
			else
				return hierarchicalToken.substring(0, paramStart)+hierarchicalToken.substring(nextControl);
		} 
		
		
		paramStart=hierarchicalToken.indexOf(PlaceHandler.QUESTION_MARK+paramName+PlaceHandler.EQUALS_SIGN);
		//first param somewhere
		if(paramStart!=-1) {
			int nextControl=nextControl(hierarchicalToken, paramStart);
			if(nextControl==-1)
				return hierarchicalToken.substring(0, paramStart);
			else
				if(PlaceHandler.AND_SIGN.equals(""+hierarchicalToken.charAt(nextControl)))
					return hierarchicalToken.substring(0, paramStart+1)+hierarchicalToken.substring(nextControl+1);
				else
					return hierarchicalToken.substring(0, paramStart)+hierarchicalToken.substring(nextControl);
		} else {
			return hierarchicalToken;
		}
	}
	
	private static int nextControl(String hierarchicalToken, int paramStart) {
		int eq = hierarchicalToken.indexOf(PlaceHandler.AND_SIGN, paramStart+1);
		int per = hierarchicalToken.indexOf(Node.ID_SEPARATOR, paramStart+1);
		
		if(eq==-1) {
			if(per==-1) {
				return -1;
			} else {
				return per;
			}
		} else {
			if(per==-1) {
				return eq;
			} else {
				return Math.min(eq, per);
			}
		}
	}

	public static String appendChild(String hierarchicalToken, String childToken) {
		if(hierarchicalToken.length()<1)
			return childToken;
		
		if(Node.ID_SEPARATOR.equals(""+hierarchicalToken.charAt(hierarchicalToken.length()-1)))
			return hierarchicalToken+childToken;
		
		return hierarchicalToken+Node.ID_SEPARATOR+childToken;
		
	}
	
	public static String getPlacePart(String currentFullToken) {
		StringBuffer sb = new StringBuffer();
		boolean firstToken=true;
		
		for(String s : currentFullToken.split(regExp(Node.ID_SEPARATOR))) {
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
		
		String[] placeParts = currentFullToken.split(regExp(Node.ID_SEPARATOR));
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
	
	public static void updateHierarchicalTokens(String currentFullToken, Node<InePlace> placeRoot) {
		if(currentFullToken==null || currentFullToken.length()<1)
			return;
		
		String[] parts;
		if(currentFullToken.indexOf(Node.ID_SEPARATOR)<0) {
			parts = new String[]{currentFullToken};
		} else  {
			parts = currentFullToken.split(PlaceHandlerHelper.regExp(Node.ID_SEPARATOR));
		}
		
		Node<InePlace> pointer = placeRoot;
		StringBuffer curreFullPart = new StringBuffer();
		for(int i=0; i<parts.length; i++) {
			String partWithoutParams = PlaceHandlerHelper.getPlacePart(parts[i]);
			
			Node<InePlace> selectedNode=null;
			for(Node<InePlace> pn : pointer.getChildren()) {
				if(partWithoutParams.equals(pn.getNodeId())) {
					selectedNode=pn;
				}
				
				pn.getNodeElement().setHierarchicalToken(PlaceHandlerHelper.appendChild(curreFullPart.toString(), pn.getNodeId()));
			}
			
			if(curreFullPart.length()>0)
				curreFullPart.append(Node.ID_SEPARATOR);
			
			curreFullPart.append(parts[i]);
			pointer=selectedNode;
		}
		
		//extra level
		if(pointer!=null && pointer.getChildren()!=null) {
			for(Node<InePlace> pn : pointer.getChildren()) {
				pn.getNodeElement().setHierarchicalToken(PlaceHandlerHelper.appendChild(curreFullPart.toString(), pn.getNodeId()));
			}
		}
	}

	public static String createSubMenuToken(String currentFullToken, String... subMenuTokens) {
		if(subMenuTokens==null || subMenuTokens.length<1)
			return currentFullToken;
		
		String res = currentFullToken;
		for(String s : subMenuTokens) {
			res = appendChild(res, s);
		}
		
		return res;
	}

	public static String createSameLevelMenuToken(String currentFullToken, String... subMenuTokens) {
		StringBuffer sb = new StringBuffer();
		
		String[] originalTokens = currentFullToken.split(regExp(Node.ID_SEPARATOR));
		for(int i=0; i<originalTokens.length-1; i++) {
			if(sb.length()>0) sb.append(Node.ID_SEPARATOR);
			sb.append(originalTokens[i]);
		}
		
		if(subMenuTokens!=null) {
			for(String token : subMenuTokens) {
				if(sb.length()>0) sb.append(Node.ID_SEPARATOR);
				sb.append(token);
			}
		}
		
		return sb.toString();
	}
	
	public static String createParentLevelMenuToken(String currentFullToken){
		String[] originalTokens = currentFullToken.split(regExp(Node.ID_SEPARATOR));
		StringBuffer newToken = new StringBuffer();
		
		for (int i = 0; i<originalTokens.length - 1; i++){
			newToken.append(originalTokens[i]);
			
			if (i != originalTokens.length - 2)
				newToken.append(Node.ID_SEPARATOR);
		}
		
		return newToken.toString();
	}
	
	public static int levelOfChange(List<String> lastTokenParts, List<String> newTokenParts){
		if (lastTokenParts == null) return 0;
		if (newTokenParts == null) return 0;
		
		
		int minLength = Math.min(lastTokenParts.size(), newTokenParts.size());
		for (int i = 0; i < minLength ; i++){
			if (!lastTokenParts.get(i).equals(newTokenParts.get(i))) {
				return i;
			}
		}
		if (lastTokenParts.size() == newTokenParts.size()) return -1;
		else return minLength;
		
	}
	
	/**
	 * Example
	 * currentToken: home/devices?deviceGroups=1&chart=false&details=true/device?deviceId=100000/general
	 * target: home/devices/device
	 * returns: home/devices?deviceGroups=1&chart=false&details=true/device?deviceId=100000
	 * 
	 */
	public static String findActualLevelWithParams(String currentToken, String target){
		String[] currentTokenParts = currentToken.split(Node.ID_SEPARATOR);
		String[] targetTokenParts = target.split(Node.ID_SEPARATOR);
		
		String splittedToken = "";
		
		for (int i = 0; i < targetTokenParts.length; i++){
			if (currentTokenParts.length < i){
				splittedToken += targetTokenParts[i] + Node.ID_SEPARATOR;
			} else if (currentTokenParts[i].startsWith(targetTokenParts[i])){
				splittedToken += currentTokenParts[i] + Node.ID_SEPARATOR;
			} else {
				splittedToken += targetTokenParts[i] + Node.ID_SEPARATOR;
			}			
		}
		if (splittedToken.length() > 0) splittedToken = splittedToken.substring(0, splittedToken.length()-1);
		
		return splittedToken;
		
	}
}
