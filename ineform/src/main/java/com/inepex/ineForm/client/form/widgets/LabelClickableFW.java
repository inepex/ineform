package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.shared.util.date.DateProvider;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class LabelClickableFW extends FormWidgetWrapperFormWidget {

    private final FlowPanel mainPanel;
    private final Label clickable = new Label(IneFormI18n.change());

    public LabelClickableFW(
        FDesc fielddescriptor,
        boolean showLongAsDate,
        String nullAlterText,
        DateProvider dateProvider) {
        super(new LabelFW(fielddescriptor, showLongAsDate, nullAlterText, dateProvider));

        mainPanel = new FlowPanel();
        mainPanel.add(getWrappedFW());
        mainPanel.add(clickable);
        initWidget(mainPanel);

        clickable.addStyleName(ResourceHelper.ineformRes().style().labelClickableFW());
    }

    public Label getClickable() {
        return clickable;
    }
}
