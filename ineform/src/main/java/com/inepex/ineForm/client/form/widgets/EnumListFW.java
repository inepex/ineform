package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.inepex.ineForm.client.form.widgets.listbox.AbstractListBoxFW;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class EnumListFW extends AbstractListBoxFW {
    public final static String enumValues = "enumValues";
    private List<String> hiddenNames = null;
    private List<String> hiddenValues = null;

    public EnumListFW(FDesc fieldDescriptor, WidgetRDesc wrDesc, String enumValues) {
        super(fieldDescriptor, wrDesc);
        int i = 0;
        TreeMap<String, Integer> sortedValues = new TreeMap<>();
        for (String value : enumValues.split(IFConsts.enumValueSplitChar)) {
            sortedValues.put(value, i++);
        }

        for (String value : sortedValues.keySet()) {
            getListBox().addItem(value, Integer.toString(sortedValues.get(value)));
        }
    }

    @Override
    public Long getLongValue() {
        if (notSelectedText.equals(getListBox().getValue(getListBox().getSelectedIndex())))
            return null;
        return Long.parseLong(getListBox().getValue(getListBox().getSelectedIndex()));
    }

    @Override
    public void setLongValue(Long value) {
        String val;

        if (value == null && !allowsNull && getListBox().getItemCount() > 0) {
            getListBox().setSelectedIndex(0);
            return;
        }

        if (value == null)
            val = notSelectedText;
        else
            val = value.toString();

        for (int i = 0; i < getListBox().getItemCount(); i++) {
            if (val.equals(getListBox().getValue(i))) {
                getListBox().setSelectedIndex(i);
                return;
            }
        }
    }

    @Override
    public boolean handlesLong() {
        return true;
    }

    public void hideItem(Long value) {
        if (value == null)
            return;

        String val = value.toString();

        for (int i = 0; i < getListBox().getItemCount(); i++) {
            if (val.equals(getListBox().getValue(i))) {
                if (hiddenNames == null)
                    hiddenNames = new ArrayList<String>();
                if (hiddenValues == null)
                    hiddenValues = new ArrayList<String>();
                hiddenNames.add(getListBox().getItemText(i));
                hiddenValues.add(val);
                getListBox().removeItem(i);
                return;
            }
        }
    }

    public void showItem(Long value) {
        if (value == null || hiddenValues == null)
            return;

        String val = value.toString();

        for (int i = 0; i < hiddenValues.size(); i++) {
            if (val.equals(hiddenValues.get(i))) {
                getListBox().addItem(hiddenNames.get(i), val);
                hiddenNames.remove(i);
                hiddenValues.remove(i);
                return;
            }
        }
    }
}
