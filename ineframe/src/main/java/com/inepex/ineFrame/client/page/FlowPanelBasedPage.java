package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.InePlace;

public abstract class FlowPanelBasedPage extends HandlerAwareComposite implements InePage {

	protected FlowPanel mainPanel = new FlowPanel(); 
	
	private boolean isFirstShow = true;
	
	protected InePlace currentPlace;
	
	protected Map<String, String> urlParams;
	
	public FlowPanelBasedPage() {
		initWidget(mainPanel);
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
		this.urlParams = urlParams;
		callback.onUrlParamsParsed();
	}
	
	@Override
	public void setCurrentPlace(InePlace place) {
		currentPlace = place;
	}

	public InePlace getCurrentPlace() {
		return currentPlace;
	}

	public Map<String, String> getUrlParams() {
		return urlParams;
	}
}
