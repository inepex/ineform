package com.inepex.ineFrame.client.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ListItemWidget extends SimplePanel implements HasClickHandlers {
    public ListItemWidget() {
        super((Element) Document.get().createLIElement().cast());
    }

    public ListItemWidget(String html) {
        this();
        setInnerHTML(html);
    }

    public ListItemWidget(Widget w) {
        this();
        this.add(w);
    }

    public void setInnerHTML(String html) {
        getElement().setInnerHTML(html);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler clickHandler) {
        return addDomHandler(clickHandler, ClickEvent.getType());
    }
}
