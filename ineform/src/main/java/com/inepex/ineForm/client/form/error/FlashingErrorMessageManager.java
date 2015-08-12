package com.inepex.ineForm.client.form.error;

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.resources.ResourceHelper;

public class FlashingErrorMessageManager implements ErrorMessageManagerInterface {

    private final FormWidget widget;

    public FlashingErrorMessageManager(FormWidget widget) {
        this.widget = widget;
    }

    @Override
    public void clearErrorMsg() {}

    @Override
    public void addErrorMsg(List<String> errorlist) {
        if (errorlist != null && errorlist.size() > 0) {
            Timer t = new Timer() {
                int i = 0;

                @Override
                public void run() {
                    if (i == 6) {
                        cancel();
                        return;
                    }

                    if (i % 2 == 0) {
                        widget.addStyleName(ResourceHelper.ineformRes().style().flashingBorder());
                    } else {
                        widget
                            .removeStyleName(ResourceHelper.ineformRes().style().flashingBorder());
                    }
                    i++;
                }
            };

            t.scheduleRepeating(100);
        }
    }

    @Override
    public void setVisible(boolean visible) {}

}
