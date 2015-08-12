package com.inepex.ineFrame.client.page;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.GreenScrollPanel;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.InePlace;

public abstract class FlowPanelBasedPageWithScroll extends HandlerAwareComposite implements InePage {

    protected GreenScrollPanel scrollPanel = new GreenScrollPanel();
    protected FlowPanel mainPanel = new FlowPanel();

    private boolean isFirstShow = true;

    protected InePlace currentPlace;

    public FlowPanelBasedPageWithScroll() {
        scrollPanel.asWidget().setHeight("100%");
        scrollPanel.setScrollContent(mainPanel);
        initWidget(scrollPanel.asWidget());
        mainPanel.getElement().getStyle().setPadding(15.0, Unit.PX);
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void onShow() {
        if (isFirstShow) {
            onShow(true);
            isFirstShow = false;
            return;
        }
        onShow(false);
    }

    protected abstract void onShow(boolean isFirstShow);

    @Override
    public void setUrlParameters(Map<String, String> urlParams, UrlParamsParsedCallback callback)
        throws Exception {
        callback.onUrlParamsParsed();
    }

    @Override
    public void setCurrentPlace(InePlace place) {
        currentPlace = place;
    }

    public InePlace getCurrentPlace() {
        return currentPlace;
    }

}
