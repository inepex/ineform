package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.misc.HandlerAwareLayoutPanel;
import com.inepex.ineFrame.client.navigation.InePlace;

/**
 * render page contents in {@link #onShow(boolean)}
 * optionally clean up resources in {@link #onUnload()}
 * override {@link #setUrlParameters(Map, com.inepex.ineFrame.client.page.InePage.UrlParamsParsedCallback)} 
 * to handle url params
 *
 */
public abstract class LayoutPanelBasedPage extends HandlerAwareLayoutPanel implements InePage{

	protected InePlace currentPlace;
	private Boolean isFirstShow = true;
	protected Map<String, String> urlParams;
	
	public LayoutPanelBasedPage() {}
	
	@Override
	public void setCurrentPlace(InePlace place) {
		this.currentPlace = place;
	}

	/**
	 * onShow called only after {@link UrlParamsParsedCallback#onUrlParamsParsed()} called
	 * override to handle url parameters
	 */
	@Override
	public void setUrlParameters(Map<String, String> urlParams,
			UrlParamsParsedCallback callback) throws Exception {
		this.urlParams = urlParams;
		callback.onUrlParamsParsed();
	}

	@Override
	public final void onShow() {
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
		
	public Map<String, String> getUrlParams() {
		return urlParams;
	}
	
	public abstract void onShow(boolean isfirstShow);
}
