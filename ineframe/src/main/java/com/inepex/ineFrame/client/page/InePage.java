package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineFrame.client.navigation.InePlace;

public interface InePage extends IsWidget{
	public void setCurrentPlace(InePlace place);
	public void setUrlParameters(Map<String, String> urlParams, UrlParamsParsedCallback callback) throws Exception;
	public void onShow();
	
	public interface UrlParamsParsedCallback {
		void onUrlParamsParsed();
		
		/**
		 * @param redirectToToken only works with ParamPlace
		 */
		void onUrlParamsParsed(String redirectToToken);
	}
}
