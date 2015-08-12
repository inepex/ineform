package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;

public class StepperPanelPageWidget extends PanelWidget {

    public static class Param {
        public static final String prevVisible = "prevVisible";
        public static final String nextVisible = "nextVisible";
        public static final String saveVisible = "saveVisible";
        public static final String cancelVisible = "cancelVisible";
        public static final String prevLabel = "prevLabel";
        public static final String nextLabel = "nextLabel";
        public static final String saveLabel = "saveLabel";
        public static final String cancelLabel = "cancelLabel";
        public static final String custButtons = "custButtons";
    }

    private final FlowPanel mainPanel = new FlowPanel();

    public StepperPanelPageWidget(
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
