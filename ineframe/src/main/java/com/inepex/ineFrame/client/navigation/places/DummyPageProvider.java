package com.inepex.ineFrame.client.navigation.places;

import com.google.inject.Provider;
import com.inepex.ineFrame.client.page.defaults.DummyPage;

public class DummyPageProvider implements Provider<DummyPage> {

    private String text;
    private DummyPage page;

    public DummyPageProvider() {}

    public DummyPageProvider(String text) {
        this.text = text;
    }

    @Override
    public DummyPage get() {
        if (page == null) {
            if (text == null)
                page = new DummyPage();
            else
                page = new DummyPage(text);
        }

        return page;
    }

}
