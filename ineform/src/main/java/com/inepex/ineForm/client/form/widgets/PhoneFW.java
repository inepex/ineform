package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineForm.shared.PhoneNumberLogic;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class PhoneFW extends DenyingFormWidget {

    public final static String COUNTRY_WIDTH = "30px";
    public final static String AREA_WIDTH = "30px";
    public final static String LOCAL_WIDTH = "100px";

    final FlowPanel mainPanel = new FlowPanel();
    final NumberTextBoxFW country = new NumberTextBoxFW(null);
    final NumberTextBoxFW area = new NumberTextBoxFW(null);
    final NumberTextBoxFW local = new NumberTextBoxFW(null);

    public PhoneFW(FDesc fielddescriptor) {
        super(fielddescriptor);
        mainPanel.add(new InlineLabel("(+"));
        mainPanel.add(country);
        mainPanel.add(new InlineLabel(") - "));
        mainPanel.add(area);
        mainPanel.add(new InlineLabel(" - "));
        mainPanel.add(local);
        initWidget(mainPanel);

        country.setWidth(COUNTRY_WIDTH);
        country.getElement().getStyle().setMarginLeft(5, Unit.PX);
        country.getElement().getStyle().setMarginRight(5, Unit.PX);

        area.setWidth(AREA_WIDTH);
        area.getElement().getStyle().setMarginLeft(5, Unit.PX);
        area.getElement().getStyle().setMarginRight(5, Unit.PX);

        local.setWidth(LOCAL_WIDTH);
        local.getElement().getStyle().setMarginLeft(5, Unit.PX);
        local.getElement().getStyle().setMarginRight(5, Unit.PX);

        country.setMaxLength(PhoneNumberLogic.MAX_COUNTRY_LENGTH);
        area.setMaxLength(PhoneNumberLogic.MAX_AREA_LENGTH);
        local.setMaxLength(PhoneNumberLogic.MAX_LOCAL_LENGTH);
    }

    @Override
    public boolean handlesString() {
        return true;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(country.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
            @Override
            public void onFormWidgetChange(FormWidgetChangeEvent e) {
                PhoneFW.this.fireFormWidgetChanged();
            }
        }));

        registerHandler(area.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
            @Override
            public void onFormWidgetChange(FormWidgetChangeEvent e) {
                PhoneFW.this.fireFormWidgetChanged();
            }
        }));

        registerHandler(local.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
            @Override
            public void onFormWidgetChange(FormWidgetChangeEvent e) {
                PhoneFW.this.fireFormWidgetChanged();
            }
        }));
    }

    @Override
    public void setEnabled(boolean enabled) {
        country.setEnabled(enabled);
        area.setEnabled(enabled);
        local.setEnabled(enabled);
    }

    @Override
    public void setStringValue(String value) {
        String[] parsed = PhoneNumberLogic.parsePhoneString(value);
        if (parsed != null) {
            country.setStringValue(parsed[0]);
            area.setStringValue(parsed[1]);
            local.setStringValue(parsed[2]);
        } else {
            country.setStringValue(null);
            area.setStringValue(null);
            local.setStringValue(null);
        }
    }

    @Override
    public String getStringValue() {
        if (country.getLongValue() == null
            && area.getLongValue() == null
            && local.getLongValue() == null)
            return null;

        StringBuilder sb = new StringBuilder();
        sb
            .append(PhoneNumberLogic.PLUS_SIGN)
            .append(country.getStringValue())
            .append(PhoneNumberLogic.PART_SEPARATOR)
            .append(area.getStringValue())
            .append(PhoneNumberLogic.PART_SEPARATOR)
            .append(local.getStringValue());

        return sb.toString();
    }
}
