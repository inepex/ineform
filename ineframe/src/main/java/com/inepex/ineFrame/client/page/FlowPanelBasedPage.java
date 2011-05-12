package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.InePlace;

public abstract class FlowPanelBasedPage extends HandlerAwareComposite implements InePage {

	protected FlowPanel mainPanel = new FlowPanel(); 

	protected int paramsToParse = 0;
	protected UrlParamsParsedCallback paramsParsedCallback = null;
	
	private boolean isFirstShow = true;
	
	protected InePlace currentPlace;
	
	public FlowPanelBasedPage() {
		initWidget(mainPanel);
	}
	
	/**
	 * Calls onUrlParamsParsed if all parameters were parsed
	 */
	protected void paramParsed() {
		paramsToParse--;
		if (paramsToParse == 0) {
			paramsParsedCallback.onUrlParamsParsed();
			paramsParsedCallback = null;
		}
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
	
	@Override
	public void onShow() {
		if (isFirstShow) {
			onShow(true);
			isFirstShow = false;
			return;
		}
		onShow(false);
	}
	
	protected abstract void onShow(boolean isFirstShow);
	
	@Override
	public void setUrlParameters(Map<String, String> urlParams,
			UrlParamsParsedCallback callback) throws Exception {
		callback.onUrlParamsParsed();
	}
	
	@Override
	public void setCurrentPlace(InePlace place) {
		currentPlace = place;
	}
	
}
