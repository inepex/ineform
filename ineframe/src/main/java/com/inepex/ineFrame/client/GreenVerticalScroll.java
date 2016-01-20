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
import com.google.gwt.user.client.ui.VerticalScrollbar;
import com.inepex.ineFrame.client.GreenScrollPanel.GreenScrollImpl;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;

/**
 * 
 * cheat:
 * http://www.google.com/codesearch#A1edwVHBClQ/user/src/com/google/gwt/user
 * /client/ui/NativeVerticalScrollbar.java
 */
public class GreenVerticalScroll extends HandlerAwareFlowPanel implements VerticalScrollbar {

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
    private int scrollPaneSize = 0;

    /**
     * for 'dragging'
     */
    private int previousMouseY = -1;
    private int previosVPos = -1;

    /**
     * for 'dragging'
     */
    private boolean isDragging = false;

    int vPos = 0;

    public GreenVerticalScroll(GreenScrollImpl greenScroll, Element scrollableContent) {
        this.scrollableContent = scrollableContent;
        this.greenScroll = greenScroll;

        scrollPanel = new FlowPanel();
        scrollPanel.setStyleName(ResourceHelper.getRes().style().scrollPanel());
        add(scrollPanel);
    }

    @Override
    public int getMaximumVerticalScrollPosition() {
        return scrollableContent.getOffsetHeight() - getElement().getOffsetHeight();
    }

    @Override
    public int getMinimumVerticalScrollPosition() {
        return 0;
    }

    @Override
    public int getVerticalScrollPosition() {
        return vPos;
    }

    @Override
    public void setVerticalScrollPosition(int position) {
        scrollPanel.getElement().getStyle().setTop(position / d, Unit.PX);
        vPos = position;
    }

    @Override
    public HandlerRegistration addScrollHandler(ScrollHandler handler) {
        return addDomHandler(handler, ScrollEvent.getType());
    }

    @Override
    public int getScrollHeight() {
        return getElement().getClientHeight();
    }

    @Override
    public void setScrollHeight(int height) {
        if (getMaximumVerticalScrollPosition() < 1 || getElement().getClientHeight() < 1)
            scrollPaneSize = 0;
        else {
            d = 1.0f + (float) scrollableContent.getScrollHeight() / getElement().getClientHeight();
            scrollPaneSize = getElement().getClientHeight()
                - Math.round((getMaximumVerticalScrollPosition() / d));
        }

        scrollPanel.getElement().getStyle().setHeight(scrollPaneSize, Unit.PX);
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
                isDragging = true;
                previousMouseY = event.getScreenY();
                previosVPos = vPos;
                event.preventDefault();
                event.stopPropagation();
                DOM.setCapture(scrollPanel.getElement());
            }
        }, MouseDownEvent.getType()));

        registerHandler(scrollPanel.addDomHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp(MouseUpEvent event) {
                isDragging = false;
                previousMouseY = -1;
                previosVPos = -1;
                DOM.releaseCapture(scrollPanel.getElement());
            }
        }, MouseUpEvent.getType()));

        registerHandler(scrollPanel.addDomHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (!isDragging)
                    return;

                if (previousMouseY == -1)
                    throw new IllegalStateException();

                int newY = event.getScreenY();
                int newPos = previosVPos + Math.round((newY - previousMouseY) * d);
                if (newPos < getMinimumVerticalScrollPosition())
                    newPos = getMinimumVerticalScrollPosition();

                if (newPos > getMaximumVerticalScrollPosition())
                    newPos = getMaximumVerticalScrollPosition();

                if (newPos != getVerticalScrollPosition())
                    greenScroll.setVerticalScrollPosition(newPos);
            }
        }, MouseMoveEvent.getType()));
    }
}
