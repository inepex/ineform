package com.inepex.ineFrame.client.navigation.places;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.page.InePage;

public abstract class WidgetPlace extends InePlace {

    @Override
    public InePage getAssociatedPage() {
        return null;
    }

    /**
     * @return null to or a widget
     */
    public abstract Widget getWidget(Map<String, String> urlParams);

    public abstract boolean isWidget(Map<String, String> urlParams);

    public abstract void update(Map<String, String> urlParams);
}
