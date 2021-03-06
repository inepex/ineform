package com.inepex.ineForm.client.form.prop;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.form.error.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.form.error.SimpleTableErrorMessageManager;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.Color;
import com.inepex.ineForm.client.general.IneCheckBox;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.types.PropFieldType;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;

public class DispRow {

    private final FlexTable rowTable;

    private final RemoveCallback removeCallback;
    private final RowValueChangeCallback rowValueChangeCallback;
    private final PropRow row;

    private final AttachHackKeyWidget attachHackKeyWidget = new AttachHackKeyWidget();
    private final TextBox keyBox = new TextBox();

    private final OdFieldTypeListBox typeBox = new OdFieldTypeListBox();

    private final FlowPanel valueBoxes = new FlowPanel();
    private final TextBox valueBox = new TextBox();
    private final IneCheckBox valueBooleanBox = new IneCheckBox();

    private final IneButton removeBtn = new IneButton(Color.TRANSPARENT, IneFormI18n.REMOVE());
    private ErrorMessageManagerInterface emm;
    private PropTooltip tooltip;

    public DispRow(
        PropRow row,
        RemoveCallback removeCallback,
        RowValueChangeCallback rowValueChangeCallback,
        FlexTable rowTable,
        boolean showTypes,
        List<String> tooltipOptions) {
        this.rowTable = rowTable;
        this.row = row;
        this.removeCallback = removeCallback;
        this.rowValueChangeCallback = rowValueChangeCallback;

        setTooltipOptions(tooltipOptions);

        createRowUI(showTypes);
        keyBox.setValue(row.getKey());
        typeBox.setValue(row.getType());
        refreshValue();

    }

    private void refreshValue() {
        String val = row.getValue();
        if (val == null)
            val = "";

        if (PropFieldType.BOOLEAN.equals(row.getType())) {
            valueBox.setVisible(false);
            valueBox.setValue("");

            valueBooleanBox.setVisible(true);
            Boolean booleanValue = false;
            try {
                booleanValue = Boolean.parseBoolean(val);
            } catch (NumberFormatException e) {
                // nothing to do
            }
            row.setValue(booleanValue.toString());
            valueBooleanBox.setValue(booleanValue);
        } else {
            valueBooleanBox.setVisible(false);
            valueBooleanBox.setValue(false);

            valueBox.setVisible(true);
            valueBox.setValue(val);
        }
    }

    public ErrorMessageManagerInterface getErrorManager() {
        return emm;
    }

    private void createRowUI(boolean showTypes) {
        int currentRow = rowTable.getRowCount();
        int col = 0;

        attachHackKeyWidget.add(keyBox);
        rowTable.setWidget(currentRow, col++, attachHackKeyWidget);

        if (showTypes)
            rowTable.setWidget(currentRow, col++, typeBox);

        valueBoxes.add(valueBox);
        valueBoxes.add(valueBooleanBox);
        rowTable.setWidget(currentRow, col++, valueBoxes);

        rowTable.setWidget(currentRow, col++, removeBtn);

        rowTable.addCell(currentRow);
        emm = createEMM(rowTable.getCellFormatter().getElement(currentRow, col++));
    }

    protected ErrorMessageManagerInterface createEMM(Element target) {
        return new SimpleTableErrorMessageManager(target);
    }

    private void setTooltipOptions(List<String> options) {
        if (options != null) {
            tooltip = new PropTooltip();
            tooltip.setOptions(options);
        }

    }

    private class AttachHackKeyWidget extends HandlerAwareFlowPanel {
        @Override
        protected void onAttach() {
            super.onAttach();

            if (tooltip != null) {
                registerHandler(tooltip.addValueChangeHandler(new ValueChangeHandler<String>() {
                    @Override
                    public void onValueChange(ValueChangeEvent<String> event) {
                        keyBox.setValue(event.getValue(), true);
                    }
                }));
            }

            registerHandler(keyBox.addValueChangeHandler(new ValueChangeHandler<String>() {

                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    row.setKey(keyBox.getValue());
                    rowValueChangeCallback.onRowValueChanged(row);
                }
            }));

            registerHandler(keyBox.addFocusHandler(new FocusHandler() {

                @Override
                public void onFocus(FocusEvent event) {
                    if (DispRow.this.tooltip != null) {
                        DispRow.this.tooltip.showRelativeTo(keyBox);
                    }
                }
            }));

            registerHandler(typeBox.addChangeHandler(new ChangeHandler() {

                @Override
                public void onChange(ChangeEvent event) {
                    row.setType(typeBox.getValue());
                    row.clearDataIfCanNotParse();
                    refreshValue();

                    rowValueChangeCallback.onRowValueChanged(row);
                }
            }));

            registerHandler(valueBox.addValueChangeHandler(new ValueChangeHandler<String>() {

                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    row.setValue(valueBox.getValue());
                    rowValueChangeCallback.onRowValueChanged(row);
                }
            }));

            registerHandler(
                valueBooleanBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

                    @Override
                    public void onValueChange(ValueChangeEvent<Boolean> event) {
                        Boolean val = valueBooleanBox.getValue();
                        row.setValue(val == null ? null : val.toString());
                        rowValueChangeCallback.onRowValueChanged(row);
                    }
                }));

            registerHandler(removeBtn.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    removeCallback.onRemove(row);
                }
            }));
        }

        @Override
        protected void onUnload() {
            if (tooltip != null)
                tooltip.hide();
            super.onUnload();
        }

    }

    /**
     * testing:
     * 
     * key (duplication, mandatory) type(mandatory) value(parsable)
     */
    public static Map<Long, String> validateRows(List<PropRow> rows) {
        Map<Long, String> ret = new TreeMap<Long, String>();
        Set<String> keys = new TreeSet<String>();

        for (PropRow r : rows) {
            String key = r.getKey();

            if (key.length() == 0) {
                ret.put(r.getInnerId(), IneFormI18n.custKVOValidateEmpty());
                continue;
            }

            if (keys.contains(key)) {
                ret.put(r.getInnerId(), IneFormI18n.custKVOValidateDuplicate());
                continue;
            }
            keys.add(key);

            if (r.getKey().indexOf('.') != -1) {
                ret.put(r.getInnerId(), IneFormI18n.custKVOValidateDot());
                continue;
            }

            if (r.getType() == null) {
                ret.put(r.getInnerId(), IneFormI18n.custKVOValidateSet());
                continue;
            }

            if (r.getValue() != null && r.getValue().length() > 0) {
                try {
                    switch (r.getType()) {
                        case BOOLEAN:
                            String v = r.getValue().toLowerCase().trim();
                            if (!"true".equals(v) && !"false".equals(v))
                                throw new NumberFormatException();
                            break;
                        case DOUBLE:
                            Double.parseDouble(r.getValue());
                            break;
                    }
                } catch (NumberFormatException e) {
                    ret.put(r.getInnerId(), IneFormI18n.custKVOValidateParse());
                    continue;
                }
            }
        }

        return ret;
    }

}