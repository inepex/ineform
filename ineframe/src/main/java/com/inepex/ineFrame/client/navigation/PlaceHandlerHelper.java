package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineom.shared.descriptor.Node;

public class PlaceHandlerHelper {
	
	public static String regExp(String str) {
		//TODO: excape special characters like \
		return "[" + str + "]";
	}
	
	public static String appendParam(String hierarchicalToken, String paramToken, String value) {
		if(hierarchicalToken.length()<1)
			return "";
		
		String lastPart;
		if(hierarchicalToken.indexOf(Node.ID_SEPARATOR)>0) {
			String[] parts = hierarchicalToken.split(regExp(Node.ID_SEPARATOR));
			lastPart = parts[parts.length-1];
		} else {
			lastPart=hierarchicalToken;
		}
		
		if(PlaceHandlerHelper.getUrlParameters(lastPart).containsKey(paramToken)) {
			StringBuffer editedLastPart = new StringBuffer();
			int paramIndex = lastPart.indexOf(paramToken,
					lastPart.indexOf(PlaceHandler.QUESTION_MARK));
			
			editedLastPart.append(lastPart.substring(0, paramIndex));
			editedLastPart.append(paramToken);
			editedLastPart.append(PlaceHandler.EQUALS_SIGN);
			editedLastPart.append(value);
			
			int at = lastPart.indexOf(PlaceHandler.AND_SIGN, paramIndex);
			if(at>0)
				editedLastPart.append(lastPart.substring(at, lastPart.length()));
			
			return hierarchicalToken.replace(lastPart, editedLastPart.toString());
		}
			
		if(lastPart.indexOf(PlaceHandler.QUESTION_MARK)>=0)
			return hierarchicalToken+PlaceHandler.AND_SIGN+paramToken+PlaceHandler.EQUALS_SIGN+value;
		else
			return hierarchicalToken+PlaceHandler.QUESTION_MARK+paramToken+PlaceHandler.EQUALS_SIGN+value;
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
	
	public static String getFirstIncorrectParamPlace(String currentFullToken, Node<InePlace> root) {
		if(currentFullToken==null || currentFullToken.length()<1)
			return null;
		
		StringBuffer sbPlace = new StringBuffer();
		StringBuffer sbFull = new StringBuffer();
		Map<String, String> params = new TreeMap<String, String>();
		
		for(String fullPart : currentFullToken.split(PlaceHandlerHelper.regExp(Node.ID_SEPARATOR))) {
			if(sbPlace.length()>0)
				sbPlace.append(Node.ID_SEPARATOR);
			sbPlace.append(PlaceHandlerHelper.getPlacePart(fullPart));
			
			if(sbFull.length()>0)
				sbFull.append(Node.ID_SEPARATOR);
			sbFull.append(fullPart);
			
			params.putAll(PlaceHandlerHelper.getUrlParameters(fullPart));
			
			Node<InePlace> n = root.findNodeByHierarchicalId(sbPlace.toString());
			if(n.getNodeElement()!=null && n.getNodeElement() instanceof ParamPlace) {
				if(!((ParamPlace)n.getNodeElement()).notifyParamChangedReturnIsParamSet(params))
					return sbFull.toString();
			}
		}
		
		return null;
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
		
		for(String token : subMenuTokens) {
			if(sb.length()>0) sb.append(Node.ID_SEPARATOR);
			sb.append(token);
		}
		
		return sb.toString();
	}
}
