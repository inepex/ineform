package com.inepex.ineForm.client.form.panelwidgets;

import java.util.List;

import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;

public interface DisplayedFormUnitChangeHandler {

    void onDisplayedFormUnitChange(
        PanelWidget sourcePanel,
        List<AbstractFormUnit> exForms,
        DisplayedFormUnitChangeResponse<?> responseObject);

    void onPanelWidgetRefreshed(PanelWidget sourcePanel);

    public abstract class DisplayedFormUnitChangeResponse<T> {

        protected final T from;
        protected final T to;

        public DisplayedFormUnitChangeResponse(T from, T to) {
            this.from = from;
            this.to = to;
        }

        public abstract void onSuccess();

        public abstract void onCancel();

        public T getFrom() {
            return from;
        }

        public T getTo() {
            return to;
        }
    }
}
