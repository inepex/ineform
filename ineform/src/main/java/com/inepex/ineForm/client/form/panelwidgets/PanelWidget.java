package com.inepex.ineForm.client.form.panelwidgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineForm.shared.types.PanelWidgetT;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

public abstract class PanelWidget extends HandlerAwareComposite
        implements DisplayedFormUnitChangeHandler {

    protected final DisplayedFormUnitChangeHandler parentHandler;
    protected final PanelWidget parent;
    protected final List<AbstractFormUnit> formUnits = new ArrayList<AbstractFormUnit>(4);
    protected final PanelWidgetRDesc descriptor;

    public PanelWidget(
        PanelWidgetRDesc descriptor,
        PanelWidget parent,
        DisplayedFormUnitChangeHandler parentHandler) {
        this.parent = parent;
        this.parentHandler = parentHandler;
        this.descriptor = descriptor;
    }

    @Override
    public void onDisplayedFormUnitChange(
        PanelWidget sourcePanel,
        List<AbstractFormUnit> exForms,
        DisplayedFormUnitChangeResponse<?> responseObject) {
        parentHandler.onDisplayedFormUnitChange(sourcePanel, exForms, responseObject);

    }

    @Override
    public void onPanelWidgetRefreshed(PanelWidget sourcePanel) {
        parentHandler.onPanelWidgetRefreshed(sourcePanel);
    }

    // for initialization
    public abstract void addToPanel(Widget w);

    // formUnit management
    public void regFormUnit(AbstractFormUnit form) {
        formUnits.add(form);
        if (parent != null)
            parent.regFormUnit(form);
    }

    public List<AbstractFormUnit> getFormUnits() {
        return formUnits;
    }

    public PanelWidgetT getPanelWidgetType() {
        return descriptor.getPanelType();
    }

    public PanelWidgetRDesc getDescriptor() {
        return descriptor;
    }

}
