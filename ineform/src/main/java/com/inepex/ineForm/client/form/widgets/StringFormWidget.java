package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.util.NumberUtilCln;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

/**
 * A FormWidget for convenience, that converts every type of value to String
 * 
 * @author istvan
 *
 */
public abstract class StringFormWidget extends FormWidget {

    public StringFormWidget(FDesc fielddescriptor) {
        super(fielddescriptor);
    }

    @Override
    protected void initWidget(Widget widget) {
        // adding div around the widget to styling
        FlowPanel fp = new FlowPanel();
        fp.add(widget);
        super.initWidget(fp);
        ;
    }

    @Override
    public Boolean getBooleanValue() {
        String str = getStringValue();
        if (str == null)
            return null;

        return str.equals(IFConsts.TRUE);
    }

    @Override
    public Double getDoubleValue() {
        String str = getStringValue();
        if (str == null)
            return null;

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public IneList getListValue() {
        return null;
    }

    @Override
    public Long getLongValue() {
        String str = getStringValue();
        if (str == null)
            return null;

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public Relation getRelationValue() {
        return null;
    }

    @Override
    public boolean handlesBoolean() {
        return true;
    }

    @Override
    public boolean handlesDouble() {
        return true;
    }

    @Override
    public boolean handlesList() {
        return false;
    }

    @Override
    public boolean handlesLong() {
        return true;
    }

    @Override
    public boolean handlesRelation() {
        return false;
    }

    @Override
    public boolean handlesString() {
        return true;
    }

    @Override
    public void setBooleanValue(Boolean value) {
        if (value == null)
            return;
        setStringValue(value ? IFConsts.TRUE : IFConsts.FALSE);

    }

    @Override
    public void setDoubleValue(Double value) {
        if (value == null)
            return;
        setStringValue(new NumberUtilCln().formatNumberToFractial(value));
    }

    @Override
    public void setLongValue(Long value) {
        if (value == null)
            return;

        if (fieldDescriptor != null || !IFConsts.KEY_ID.equals(fieldDescriptor.getKey()))
            setStringValue(new NumberUtilCln().formatNumberGroupThousands(value));
        else
            setStringValue(value.toString());
    }

    @Override
    public boolean isReadOnlyWidget() {
        return false;
    }

    @Override
    public void setListValue(IneList value) {}

    @Override
    public void setRelationValue(Relation value) {}
}
