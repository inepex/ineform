package com.inepex.ineForm.client.form;

import java.util.List;

import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.panelwidgets.DisplayedFormUnitChangeHandler.DisplayedFormUnitChangeResponse;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;

/**
 * DON'T forget invoke responseObject onSuccess or onCancel method, after your
 * custom behave
 * 
 */
public interface CustomCode {

    /**
     * DON'T forget invoke responseObject onSuccess or onCancel method, after
     * your custom behave
     * 
     */
    void execute(
        IneForm form,
        PanelWidget sourcePanel,
        List<AbstractFormUnit> exForms,
        DisplayedFormUnitChangeResponse<?> responseObject);
}
