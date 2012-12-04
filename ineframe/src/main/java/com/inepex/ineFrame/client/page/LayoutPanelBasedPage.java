package com.inepex.ineFrame.client.page;

import java.util.Map;

import org.apache.tools.ant.taskdefs.condition.IsFileSelected;

import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.misc.HandlerAwareLayoutPanel;
import com.inepex.ineFrame.client.navigation.InePlace;

public class LayoutPanelBasedPage extends HandlerAwareLayoutPanel implements InePage{

	protected InePlace currentPlace;
	protected Boolean isFirstShow = true;
	
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
		if(isFirstShow){
			isFirstShow = false;
			onShow(true);
		}else{
			onShow(false);
		}
		
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public InePlace getCurrentPlace() {
		return currentPlace;
	}
	public void onShow(boolean isfirstShow){
		
	}
}
