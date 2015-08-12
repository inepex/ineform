package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface Notification extends IsWidget {
    void setContent(Widget content);

    void show();

    void hide();
}
