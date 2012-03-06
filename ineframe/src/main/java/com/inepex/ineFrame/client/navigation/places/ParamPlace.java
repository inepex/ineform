package com.inepex.ineFrame.client.navigation.places;

import java.util.Map;

import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.page.InePage.UrlParamsParsedCallback;

/**
 * Extend ParamPlace to get a dynamic variable in the placetoken hierarchy.
 * 
 * Use {@link ParamPlace#getAssociatedPage()} to provide a selector widget.
 * 
 * Set isSelectorPage to true to show the selector on the leftside, set it to false to show it as a page.
 * 
 * Handle param change in {@link ParamPlace#processParams(String, Map, UrlParamsParsedCallback)}.
 * 
 * @author SoTi
 */
public abstract class ParamPlace extends InePlace {
	
	protected boolean isSelectorPage = false;
		
	public ParamPlace(boolean isSelectorPage) {
		super();
		this.isSelectorPage = isSelectorPage;
	}

	/**
	 * Parse urlParams and decide which place to redirect.  
	 */
	public abstract void processParams(String requestedToken, Map<String, String> urlParams, UrlParamsParsedCallback callback);

	public boolean isSelectorPage() {
		return isSelectorPage;
	}
	
}
