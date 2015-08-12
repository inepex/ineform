package com.inepex.ineForm.client.form.factories;

import com.inepex.ineForm.client.form.panelwidgets.DisplayedFormUnitChangeHandler;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;

public interface PanelWidgetFactory {

    public PanelWidget createPanel(
        PanelWidgetRDesc paneldesc,
        PanelWidget parent,
        DisplayedFormUnitChangeHandler handler);

}
