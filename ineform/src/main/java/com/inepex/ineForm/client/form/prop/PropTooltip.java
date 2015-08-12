package com.inepex.ineForm.client.form.prop;

import java.util.List;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.inepex.ineFrame.client.misc.HandlerHandler;

public class PropTooltip extends PopupPanel implements HasValue<String> {

    private class Item extends Label {
        private String option;
        private HandlerHandler handlerHandler = new HandlerHandler();

        public Item(String option) {
            super();
            setText(option);
            this.option = option;
            getElement().getStyle().setCursor(Cursor.POINTER);
            getElement().getStyle().setPadding(3, Unit.PX);
        }

        @Override
        protected void onLoad() {
            super.onLoad();
            handlerHandler.registerHandler(addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    value = option;
                    ValueChangeEvent.fire(PropTooltip.this, option);
                    PropTooltip.this.hide();
                }
            }));
        }

        @Override
        protected void onUnload() {
            handlerHandler.unregister();
            super.onUnload();
        }

    }

    private FlowPanel panel = new FlowPanel();
    private String value;

    public PropTooltip() {
        super(true);
        setWidget(panel);
        panel.getElement().getStyle().setPadding(3.0, Unit.PX);
        panel.getElement().getStyle().setBackgroundColor("white");
        panel.getElement().getStyle().setBorderColor("#CCCCCC");
        panel.getElement().getStyle().setBorderWidth(1.0, Unit.PX);
        panel.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
    }

    public void setOptions(List<String> options) {
        panel.clear();
        for (String option : options) {
            panel.add(new Item(option));
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setValue(String value, boolean fireEvents) {
        this.value = value;
        if (fireEvents) {
            ValueChangeEvent.fire(PropTooltip.this, value);
        }

    }

}
