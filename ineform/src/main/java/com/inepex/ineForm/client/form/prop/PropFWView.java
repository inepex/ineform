package com.inepex.ineForm.client.form.prop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.error.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.Color;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.util.SharedUtil;

public class PropFWView extends HandlerAwareFlowPanel implements PropFW.View {

    private final Map<Long, DispRow> rowsByInnerId = new HashMap<Long, DispRow>();
    private List<Long> dispRowinnerIdMirror = new ArrayList<Long>();

    private final FlexTable rowTable = new FlexTable();

    private final IneButton addBtn = new IneButton(Color.TRANSPARENT, IneFormI18n.ADD());

    private RemoveCallback removeCallback;
    private AddCallback addCallback;
    private RowValueChangeCallback rowValueChangeCallback;

    private final PropFWReadOnlyView readOnlyView = new PropFWReadOnlyView();
    private final boolean showType;
    private List<String> tooltipOptions;

    @Inject
    public PropFWView() {
        this(true);
    }

    protected PropFWView(boolean showType) {
        this.showType = showType;

        rowTable.setStyleName(ResourceHelper.ineformRes().style().customKVOTable());
        rowTable.getColumnFormatter().setStyleName(
            0,
            ResourceHelper.ineformRes().style().customKVOTableContent());
        rowTable.getColumnFormatter().setStyleName(
            1,
            ResourceHelper.ineformRes().style().customKVOTableContent());
        rowTable.getColumnFormatter().setStyleName(
            2,
            ResourceHelper.ineformRes().style().customKVOTableContent());
        rowTable.getColumnFormatter().setStyleName(
            3,
            ResourceHelper.ineformRes().style().customKVOTableContent());

        addBtn.getElement().getStyle().setMarginTop(15, Unit.PX);
    }

    private void createHeader() {
        int col = 0;
        rowTable.setWidget(0, col++, new Label(IneFormI18n.customKVO_key()));
        if (showType)
            rowTable.setWidget(0, col++, new Label(IneFormI18n.customKVO_type()));
        rowTable.setWidget(0, col++, new Label(IneFormI18n.customKVO_value()));
        ((FlexCellFormatter) rowTable.getCellFormatter()).setColSpan(0, col - 1, 2);

        rowTable.getCellFormatter().setStyleName(
            0,
            0,
            ResourceHelper.ineformRes().style().customKVOHeader());
        if (showType) {
            rowTable.getCellFormatter().setStyleName(
                0,
                1,
                ResourceHelper.ineformRes().style().customKVOHeaderType());
            rowTable.getCellFormatter().setStyleName(
                0,
                2,
                ResourceHelper.ineformRes().style().customKVOHeader());
        } else {
            rowTable.getCellFormatter().setStyleName(
                0,
                1,
                ResourceHelper.ineformRes().style().customKVOHeader());
        }

    }

    @Override
    public void setRemoveCallback(RemoveCallback removeCallback) {
        this.removeCallback = removeCallback;
    }

    @Override
    public void setAddCallback(AddCallback addCallback) {
        this.addCallback = addCallback;
    }

    @Override
    public void setRowValueChangeCallback(RowValueChangeCallback rowValueChangeCallback) {
        this.rowValueChangeCallback = rowValueChangeCallback;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(addBtn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                addCallback.onAdd();
            }
        }));
    }

    @Override
    public void dealResult(Map<Long, String> res) {
        for (DispRow dr : rowsByInnerId.values())
            dr.getErrorManager().clearErrorMsg();

        for (Long i : res.keySet()) {
            rowsByInnerId.get(i).getErrorManager().addErrorMsg(SharedUtil.Li(res.get(i)));
        }
    }

    @Override
    public ErrorMessageManagerInterface getErrorManager(PropRow r) {
        return rowsByInnerId.get(r.getInnerId()).getErrorManager();
    }

    @Override
    public void clearRows() {
        dispRowinnerIdMirror.clear();
        rowsByInnerId.clear();

        // remove all rows except the header
        for (int i = rowTable.getRowCount() - 1; i > 0; i--)
            rowTable.removeRow(i);
    }

    @Override
    public void addRow(PropRow r) {
        DispRow dr = createDispRow(
            r,
            removeCallback,
            rowValueChangeCallback,
            rowTable,
            showType,
            tooltipOptions);
        rowsByInnerId.put(r.getInnerId(), dr);
        dispRowinnerIdMirror.add(r.getInnerId());
    }

    protected DispRow createDispRow(
        PropRow row,
        RemoveCallback removeCallback,
        RowValueChangeCallback rowValueChangeCallback,
        FlexTable rowTable,
        boolean showType,
        List<String> tooltipOptions) {
        return new DispRow(
            row,
            removeCallback,
            rowValueChangeCallback,
            rowTable,
            showType,
            tooltipOptions);
    }

    @Override
    public void removeRow(PropRow r) {
        rowsByInnerId.remove(rowsByInnerId.get(r.getInnerId()));

        int index = dispRowinnerIdMirror.indexOf(r.getInnerId());

        dispRowinnerIdMirror.remove(index);

        // because of header
        index++;
        rowTable.removeRow(index);
    }

    @Override
    public void showReadOnly(List<PropRow> rows) {
        clear();
        add(readOnlyView);
        readOnlyView.init(rows, true, true);
        readOnlyView.show();

    }

    @Override
    public void showEditable() {
        addBtn.setEnabled(true);
        clear();
        add(rowTable);
        add(addBtn);

        createHeader();
    }

    @Override
    public void disableAddBtn() {
        addBtn.setEnabled(false);
    }

    @Override
    public void setTooltipOptions(List<String> options) {
        this.tooltipOptions = options;
    }
}
