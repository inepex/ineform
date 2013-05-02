package com.inepex.ineFrame.client.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class UnorderedListWidget extends ComplexPanel implements HasClickHandlers{
	public UnorderedListWidget()
	{
		setElement(Document.get().createULElement());
	}

	public void setId(String id)
	{
		// Set an attribute common to all tags
		getElement().setId(id);
	}

	public void setDir(String dir)
	{
		// Set an attribute specific to this tag
		((UListElement) getElement().cast()).setDir(dir);
	}

	@Override
	public void add(Widget w)
	{
		// ComplexPanel requires the two-arg add() method
		super.add(w, getElement());
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler clickHandler) {
		return addDomHandler(clickHandler, ClickEvent.getType());
	}
}

