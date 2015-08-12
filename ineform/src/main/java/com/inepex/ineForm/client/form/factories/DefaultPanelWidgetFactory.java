package com.inepex.ineForm.client.form.factories;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.panelwidgets.DisplayedFormUnitChangeHandler;
import com.inepex.ineForm.client.form.panelwidgets.FlowPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.HorizontalPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.PlaceholderPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelPageWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.TabPageWidget;
import com.inepex.ineForm.client.form.panelwidgets.TabPanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.VerticalPanelWidget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class DefaultPanelWidgetFactory implements PanelWidgetFactory {

    protected PanelWidgetT getTypeFromDescriptor(PanelWidgetRDesc paneldesc) {
        PanelWidgetT type;

        // DEFAULT
        if (paneldesc == null || paneldesc.getPanelType() == null)
            type = IneFormProperties.defPanelWidgetType;
        else
            type = paneldesc.getPanelType();

        return type;
    }

    protected PanelWidgetRDesc checkAndSetDefaultType(PanelWidgetRDesc paneldesc) {
        PanelWidgetT type;

        // DEFAULT
        if (paneldesc == null || paneldesc.getPanelType() == null)
            type = IneFormProperties.defPanelWidgetType;
        else
            type = paneldesc.getPanelType();

        if (paneldesc == null) {
            paneldesc = new PanelWidgetRDesc(type);
        } else {
            paneldesc.setPanelType(type);
        }
        return paneldesc;
    }

    @Override
    public PanelWidget createPanel(
        PanelWidgetRDesc paneldesc,
        PanelWidget parent,
        DisplayedFormUnitChangeHandler handler) {

        PanelWidgetRDesc updatedDesc = checkAndSetDefaultType(paneldesc);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.FLOWPANEL))
            return new FlowPanelWidget(paneldesc, parent, handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.VERTICALPANEL))
            return new VerticalPanelWidget(paneldesc, parent, handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.HORIZONTALPANEL))
            return new HorizontalPanelWidget(paneldesc, parent, handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.PLACEHOLDERPANEL))
            return new PlaceholderPanelWidget(paneldesc, parent, paneldesc, handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.TABPANEL))
            return new TabPanelWidget(paneldesc, parent, handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.TABPAGE))
            return new TabPageWidget(paneldesc, parent, paneldesc.getDisplayName(), handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.STEPPERPAGE))
            return new StepperPanelPageWidget(paneldesc, parent, handler);

        if (updatedDesc.getPanelType().equals(PanelWidgetT.STEPPERPANEL))
            return new StepperPanelWidget(paneldesc, parent, handler);

        return null;
    }

}
