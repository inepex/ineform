package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;

public class DefaultIneFrameNotification extends FlowPanel implements Notification {

    public DefaultIneFrameNotification() {
        show();
        addDomHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DefaultIneFrameNotification.this.hide();
            }

        }, ClickEvent.getType());
    }

    @Override
    public void setContent(Widget content) {
        add(content);
    }

    @Override
    public void show() {
        addStyleName(ResourceHelper.getRes().style().slider());
    }

    @Override
    public void hide() {
        addStyleName(ResourceHelper.getRes().style().closed());
    }

}
