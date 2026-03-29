package com.inepex.translatorapp.client.page;

import jakarta.inject.Inject;
import com.inepex.ineFrame.client.page.defaults.DummyPage;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public class PageNotFoundPage extends DummyPage {

    @Inject
    public PageNotFoundPage() {
        super("<h2>" + translatorappI18n.pageNotFound() + "</h2>");
    }
}
