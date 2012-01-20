package com.inepex.ineFrame.client.misc;

import com.google.gwt.event.shared.GwtEvent;

public class WindowResizeEvent extends GwtEvent<WindowResizeHandler>{
	
	private int width = -1;
	private int height = -1;
	
	public WindowResizeEvent() {
	}

	public WindowResizeEvent(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public static final Type<WindowResizeHandler> TYPE = new Type<WindowResizeHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WindowResizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(WindowResizeHandler handler) {
		handler.onResize(this);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
