package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;

public class FlowPanelWidget extends PanelWidget {

    private final FlowPanel mainPanel = new FlowPanel();

    public FlowPanelWidget(
        PanelWidgetRDesc descriptor,
        PanelWidget parent,
        DisplayedFormUnitChangeHandler handler) {
        super(descriptor, parent, handler);
        initWidget(mainPanel);
    }

    @Override
    public void addToPanel(Widget w) {
        mainPanel.add(w);
    }
}
