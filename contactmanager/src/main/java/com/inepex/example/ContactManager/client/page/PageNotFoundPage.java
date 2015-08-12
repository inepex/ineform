package com.inepex.example.ContactManager.client.page;

import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class PageNotFoundPage extends FlowPanelBasedPage {

    private HTML message = new HTML("<h1>" + CMI18n.pageNotFound() + "</h1>");

    @Inject
    public PageNotFoundPage() {

    }

    @Override
    protected void onShow(boolean isFirstShow) {
        mainPanel.clear();
        mainPanel.add(message);
    }

}
