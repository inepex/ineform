package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * {@link PlaceRequestEvent} is used in IneFrame based applications to navigate among the hierarchical places, or change
 * parameters on the current place.<br/><br/>
 * 
 * {@link PlaceRequestEvent} holds the parts of the hierarchical token that unambiguously determines a place in the hierarchy<br/><br/>
 * 
 * {@link PlaceRequestEvent} can also be by just with setting the full token (with parameters) as the hierarchical token part.
 * {@link PlaceHandler} will handle also that case correctly.
 *
 */
public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler>{
	
	public static Type<PlaceRequestHandler> TYPE = new Type<PlaceRequestHandler>();
	
	private String hierarchicalTokensWithParam;
	boolean openInNewWindow = false;
	boolean needWindowReload = false;
	
	public PlaceRequestEvent() {
	}
		
	public PlaceRequestEvent(boolean openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}

	public PlaceRequestEvent(PlaceToken placeToken) {
		this(placeToken.toString());
	}
	
	public PlaceRequestEvent(String hierarchicalTokensWithParam) {
		this.hierarchicalTokensWithParam=hierarchicalTokensWithParam;
	}
	
	public PlaceRequestEvent(boolean openInNewWindow, String hierarchicalTokensWithParam) {
		this.openInNewWindow=openInNewWindow;
		this.hierarchicalTokensWithParam=hierarchicalTokensWithParam;
	}

	@Override
	protected void dispatch(PlaceRequestHandler handler) {
		handler.onPlaceRequest(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PlaceRequestHandler> getAssociatedType() {
		return TYPE;
	}

	public String getHierarchicalTokensWithParam() {
		return hierarchicalTokensWithParam;
	}
	
	public void setNeedWindowReload(boolean needWindowReload) {
		this.needWindowReload = needWindowReload;
	}
	
	public boolean isNeedWindowReload() {
		return needWindowReload;
	}
	
	public void setHierarchicalTokensWithParam(
			String hierarchicalTokensWithParam) {
		this.hierarchicalTokensWithParam = hierarchicalTokensWithParam;
	}

	public boolean isOpenInNewWindow() {
		return openInNewWindow;
	}

	public void setOpenInNewWindow(boolean openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}
}
