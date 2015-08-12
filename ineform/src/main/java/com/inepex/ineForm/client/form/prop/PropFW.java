package com.inepex.ineForm.client.form.prop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineForm.client.form.error.ErrorMessageManagerInterface;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.PropFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

public class PropFW extends PropFWBase
    implements
    AddCallback,
    RemoveCallback,
    RowValueChangeCallback {

    public static final String PROP_TOOLTIP_PROP = "CUST_KVO_TOOLTIP_PROP";
    public static final String PROP_SEPARATOR = ";";

    public static interface View extends IsWidget {
        public void setRemoveCallback(RemoveCallback removeCallback);

        public void setRowValueChangeCallback(RowValueChangeCallback rowValueChangeCallback);

        public void setAddCallback(AddCallback addCallback);

        public void clearRows();

        public void addRow(PropRow r);

        public void removeRow(PropRow r);

        public void dealResult(Map<Long, String> res);

        public ErrorMessageManagerInterface getErrorManager(PropRow r);

        public void disableAddBtn();

        public void showReadOnly(List<PropRow> rows);

        public void showEditable();

        public void add(IsWidget widget);

        public void setTooltipOptions(List<String> options);

    }

    private final View view;

    private boolean enabled = true;

    public PropFW(PropFDesc fieldDescriptor, WidgetRDesc widgetRDesc, View view) {
        super(fieldDescriptor, widgetRDesc);

        this.view = view;
        this.view.setAddCallback(this);
        this.view.setRemoveCallback(this);
        this.view.setRowValueChangeCallback(this);
        if (widgetRDesc.hasProp(PROP_TOOLTIP_PROP)) {
            view.setTooltipOptions(Arrays.asList(widgetRDesc.getPropValue(PROP_TOOLTIP_PROP).split(
                PROP_SEPARATOR)));
        }

        // for pure java junit tests
        if (GWT.isClient())
            initWidget(view.asWidget());

        view.showEditable();
    }

    @Override
    protected void beforeParsed() {
        view.clearRows();
        view.disableAddBtn();
    }

    @Override
    protected void onParsed() {
        for (PropRow r : rows) {
            view.addRow(r);
        }
        if (!enabled)
            view.showReadOnly(rows);
        else
            view.showEditable();

    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled)
            view.showEditable();
        else
            view.showReadOnly(rows);
    }

    @Override
    public void onRowValueChanged(PropRow row) {
        fireFormWidgetChanged();
    }

    @Override
    public void onAdd() {
        fireFormWidgetChanged();
        PropRow newRow = new PropRow();
        rows.add(newRow);
        view.addRow(newRow);
        fireFormWidgetChanged();
    }

    @Override
    public void onRemove(PropRow r) {
        fireFormWidgetChanged();
        rows.remove(r);
        view.removeRow(r);
        fireFormWidgetChanged();
    }

    @Override
    public String getStringValue() {
        JSONObject json = new JSONObject();
        for (PropRow row : rows) {
            JSONValue value = null;
            switch (row.getType()) {
                case BOOLEAN:
                    value = JSONBoolean.getInstance(Boolean.parseBoolean(row.getValue()));
                    break;
                case DOUBLE:
                    value = new JSONNumber(Double.parseDouble(row.getValue()));
                    break;
                case STRING:
                    value = new JSONString(row.getValue());
                    break;
            }
            json.put(row.getKey(), value);
        }
        return json.toString();
    }

    /**
     * @return false if it's in inconsistent state
     */
    public boolean validateConsistence() {
        Map<Long, String> res = DispRow.validateRows(rows);
        view.dealResult(res);

        return res.isEmpty();
    }

    public Collection<? extends String> getModelKeys(String prefix) {
        ArrayList<String> rowKeys = new ArrayList<String>();
        for (PropRow r : rows)
            rowKeys.add(prefix + SharedUtil.ID_PART_SEPARATOR + r.getKey());

        return rowKeys;
    }

    public TreeMap<String, ErrorMessageManagerInterface> getErrorManagers(String prefix) {
        TreeMap<String, ErrorMessageManagerInterface> ret =
            new TreeMap<String, ErrorMessageManagerInterface>();
        for (PropRow r : rows)
            ret.put(prefix + SharedUtil.ID_PART_SEPARATOR + r.getKey(), view.getErrorManager(r));

        return ret;
    }

    public View getView() {
        return view;
    }

}
