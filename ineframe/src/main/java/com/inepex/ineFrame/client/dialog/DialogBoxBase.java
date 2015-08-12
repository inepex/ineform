package com.inepex.ineFrame.client.dialog;

/**
 *
 */

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;

/**
 * Base class for pretty dialogs
 * 
 * @author Horváth Szabolcs, Szoboszlai Istvan
 * 
 */
public abstract class DialogBoxBase extends DialogBox {

    protected HTML message = new HTML();
    protected VerticalPanel panel = new VerticalPanel();
    protected HorizontalPanel buttonBar = new HorizontalPanel();
    protected Button okButton = new Button(IneFrameI18n.dialogOkButton());

    public DialogBoxBase() {
        super();

        getElement().getStyle().setBackgroundColor("white");
        getElement().getStyle().setPadding(15, Unit.PX);
        getElement().getStyle().setBorderColor("black");
        getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
        getElement().getStyle().setBorderWidth(1, Unit.PX);

        setModal(true);
        setGlassEnabled(true);
        setAnimationEnabled(true);
        setAutoHideEnabled(false);

        panel.setSpacing(10);

        HorizontalPanel messagePanel = new HorizontalPanel();
        Image image = new Image(getImageResource());
        messagePanel.add(image);
        messagePanel.add(message);
        messagePanel.setCellVerticalAlignment(image, HorizontalPanel.ALIGN_MIDDLE);
        messagePanel.setCellVerticalAlignment(message, HorizontalPanel.ALIGN_MIDDLE);

        panel.add(messagePanel);

        buttonBar.setSpacing(5);
        buttonBar.add(okButton);

        panel.add(buttonBar);
        panel.setCellHorizontalAlignment(buttonBar, VerticalPanel.ALIGN_CENTER);

        okButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        configureButtonBar();

        add(panel);
    }

    protected abstract void configureButtonBar();

    protected abstract ImageResource getImageResource();

    @Override
    public void show() {
        super.show();
        center();
    }

    public void show(String message) {
        setMessage(message);
        this.show();
    }

    public void setMessage(String value) {
        message.setHTML(value);
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return okButton.addClickHandler(handler);
    }

    public void show(String message, ClickHandler okHandler) {
        show(message);
        addClickHandler(okHandler);
    }
}
