package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.InlineHTML;

public class DummyFW extends DenyingFormWidget {

    InlineHTML dummyWidget = new InlineHTML();

    public DummyFW() {
        super(null);
        initWidget(dummyWidget);
        shouldRender = false;
    }

    @Override
    public boolean isReadOnlyWidget() {
        return true;
    }

}
