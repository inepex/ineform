package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;
import com.inepex.ineForm.client.i18n.IneFormI18n;

class OOO_OMField extends FlowPanel implements DateTimeFieldInterface {

    private final Precision PRECISION;

    private final IneDateGWT inedate;

    private final DateTimeFieldParentInterface parent;

    private HandlerRegistration hr_valuechange;

    private final ListBox lb = new ListBox(false);

    public OOO_OMField(IneDateGWT date, DateTimeFieldParentInterface parent) {

        this.PRECISION = Precision.OOO_OM;
        this.inedate = date;
        this.parent = parent;

        lb.addItem("-");

        for (int i = 0; i < 60; i++) {
            if (i < 10)
                lb.addItem("0" + i);
            else
                lb.addItem("" + i);
        }

        add(lb);
        add(new InlineHTML("&nbsp;" + IneFormI18n.minutes()));
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        hr_valuechange = lb.addChangeHandler(new ListFieldChangeHandler());
    }

    @Override
    protected void onDetach() {
        super.onDetach();

        if (hr_valuechange != null) {
            hr_valuechange.removeHandler();
            hr_valuechange = null;
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    private class ListFieldChangeHandler implements ChangeHandler {

        @SuppressWarnings("deprecation")
        @Override
        public void onChange(ChangeEvent event) {
            Date d = new Date();
            d.setMinutes(lb.getSelectedIndex() - 1);
            inedate.setDate(PRECISION, d);
            parent.childValueChanged(true, false);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isNull() {
        return lb.getSelectedIndex() < 1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void refresh(boolean empty, boolean initialValue) {
        if (!initialValue)
            return;

        if (empty)
            lb.setSelectedIndex(0);
        else
            lb.setSelectedIndex(inedate.getDateClone().getMinutes() + 1);
    }

    @Override
    public boolean isTextBox() {
        return false;
    }

    @Override
    public boolean isFocusable() {
        return false;
    }

    @Override
    public boolean isInReadOnlyMode() {
        return false;
    }

    @Override
    public void setFocus(boolean focused) {
        lb.setFocus(focused);
    }

    @Override
    public void setEnabled(boolean enabled) {
        lb.setEnabled(enabled);
    }
}
