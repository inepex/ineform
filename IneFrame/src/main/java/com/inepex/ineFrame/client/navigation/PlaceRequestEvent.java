package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.shared.GwtEvent;

/**
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
	
	private String[] hierarchicalTokenParts;
	
	boolean openInNewWindow = false;
	
	
	/**
	 * Key, value series. So length of array should be multiple of 2!
	 */
	private String[] parameters;
	
	public PlaceRequestEvent() {
		
	}
		
	public PlaceRequestEvent(boolean openInNewWindow) {
		super();
		this.openInNewWindow = openInNewWindow;
	}

	public PlaceRequestEvent(String hierarchicalToken,
			String... parameters) {
		super();
		hierarchicalTokenParts = new String[1];
		this.hierarchicalTokenParts[0] = hierarchicalToken;
		this.parameters = parameters;
	}

	@Override
	protected void dispatch(PlaceRequestHandler handler) {
		handler.onPlaceRequest(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PlaceRequestHandler> getAssociatedType() {
		return TYPE;
	}

	public String[] getHierarchicalToken() {
		return hierarchicalTokenParts;
	}

	public String[] getParameters() {
		return parameters;
	}
	
	public PlaceRequestEvent setHierarchicalTokenParts(String... hierarchicalTokenParts) {
		this.hierarchicalTokenParts = hierarchicalTokenParts;
		return this;
	}

	/**
	 * Parameters are stored in key, value series. So length of array should be multiple of 2!
	 */
	public PlaceRequestEvent setParameters(String... parameters) {
		this.parameters = parameters;
		return this;
	}

	public boolean isOpenInNewWindow() {
		return openInNewWindow;
	}

	public void setOpenInNewWindow(boolean openInNewWindow) {
		this.openInNewWindow = openInNewWindow;
	}
	
	
}
