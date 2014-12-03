package com.inepex.ineFrame.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalScrollbar;
import com.inepex.ineFrame.client.GreenScrollPanel.GreenScrollImpl;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;

public class GreenHorizontalScroll extends HandlerAwareFlowPanel implements HorizontalScrollbar {

	private final Element scrollableContent;
	private final GreenScrollImpl greenScroll;
	
	/**
	 * the green rectangle (which can be dragged)
	 */
	private final FlowPanel scrollPanel;
	
	/**
	 * one pixel step on scroll bar should be 'd' pixel step on scroll content
	 */
	private float d = 0;
	
	/**
	 * size of the green rectangle
	 */
	private int scrollPaneSize=0;
	
	/**
	 * for 'dragging'
	 */
	private int previousMouseX=-1;
	private int previosHPos=-1;
	
	/**
	 * for 'dragging'
	 */
	private boolean isDragging=false;
	
	int hPos = 0;
	
	public GreenHorizontalScroll(GreenScrollImpl greenScroll, Element scrollableContent) {
		this.scrollableContent=scrollableContent;
		this.greenScroll=greenScroll;
		
		scrollPanel=new FlowPanel();
		scrollPanel.setStyleName(ResourceHelper.getRes().style().horizontalScrollPanel());
		add(scrollPanel);
	}
	
	@Override
	public int getHorizontalScrollPosition() {
		return hPos;
	}

	@Override
	public int getMaximumHorizontalScrollPosition() {
		return scrollableContent.getOffsetWidth() - getElement().getOffsetWidth();
	}

	@Override
	public int getMinimumHorizontalScrollPosition() {
		return 0;
	}

	@Override
	public void setHorizontalScrollPosition(int position) {
		scrollPanel.getElement().getStyle().setLeft(position/d, Unit.PX);
		hPos=position;
	}

	@Override
	public HandlerRegistration addScrollHandler(ScrollHandler handler) {
		return addDomHandler(handler, ScrollEvent.getType());
	}

	@Override
	public int getScrollWidth() {
		return getElement().getClientWidth();
	}

	@Override
	public void setScrollWidth(int width) {
		if(getMaximumHorizontalScrollPosition()<1 || getElement().getClientWidth() < 1)
			scrollPaneSize=0;
		else {
			d=1.0f+(float) scrollableContent.getScrollWidth()/getElement().getClientWidth();
			scrollPaneSize=getElement().getClientWidth()-Math.round((getMaximumHorizontalScrollPosition()/d));
		}
		
		scrollPanel.getElement().getStyle().setWidth(scrollPaneSize, Unit.PX);
	}
	
	public void setScrollPaneVisible(boolean visible) {
		scrollPanel.setVisible(visible);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(scrollPanel.addDomHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				isDragging=true;
				previousMouseX=event.getScreenX();
				previosHPos=hPos;
				event.preventDefault();
				event.stopPropagation();
				DOM.setCapture(scrollPanel.getElement());
			}
		}, MouseDownEvent.getType()));
		
		registerHandler(scrollPanel.addDomHandler(new MouseUpHandler() {
			
			@Override
			public void onMouseUp(MouseUpEvent event) {
				isDragging=false;
				previousMouseX=-1;
				previosHPos=-1;
				DOM.releaseCapture(scrollPanel.getElement());
			}
		}, MouseUpEvent.getType()));
		
		registerHandler(scrollPanel.addDomHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if(!isDragging)
					return;
				
				if(previousMouseX==-1)
					throw new IllegalStateException();
				
				int newX = event.getScreenX();
				int newPos = previosHPos + Math.round((newX - previousMouseX)*d);
				if(newPos<getMinimumHorizontalScrollPosition())
					newPos=getMinimumHorizontalScrollPosition();
				
				if(newPos>getMaximumHorizontalScrollPosition())
					newPos=getMaximumHorizontalScrollPosition();
				
				if(newPos!=getHorizontalScrollPosition())
					greenScroll.setHorizontalScrollPosition(newPos);
			}
		}, MouseMoveEvent.getType()));
	}

}
