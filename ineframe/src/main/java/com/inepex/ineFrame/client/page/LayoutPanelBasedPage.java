package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.misc.HandlerAwareLayoutPanel;
import com.inepex.ineFrame.client.navigation.InePlace;

public class LayoutPanelBasedPage extends HandlerAwareLayoutPanel implements InePage{

	protected InePlace currentPlace;
	
	public LayoutPanelBasedPage() {}
	
	@Override
	public void setCurrentPlace(InePlace place) {
		this.currentPlace = place;
	}

	@Override
	public void setUrlParameters(Map<String, String> urlParams,
			UrlParamsParsedCallback callback) throws Exception {
		callback.onUrlParamsParsed();
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public InePlace getCurrentPlace() {
		return currentPlace;
	}
}
