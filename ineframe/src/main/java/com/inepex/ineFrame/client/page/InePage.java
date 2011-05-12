package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.navigation.InePlace;

public interface InePage {
	public void setCurrentPlace(InePlace place);
	public void setUrlParameters(Map<String, String> urlParams, UrlParamsParsedCallback callback) throws Exception;
	public void onShow();
	public Widget asWidget();
	
	public interface UrlParamsParsedCallback {
		void onUrlParamsParsed();
	}
}
