package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.table.AbstractIneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.DummyDataConnector;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.shared.render.DefaultTableFieldRenderer;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class IneTableFW extends DenyingFormWidget {

    public static final String PROP_SINGLESELECT = "singleSelect";

    private final IneTable ineTable;
    private final DummyDataConnector connector;

    private IneList val;

    public IneTableFW(
        FDesc fielddescriptor,
        FormContext context,
        String objectDescriptorName,
        boolean singleselect) {

        super(fielddescriptor);

        connector = new DummyDataConnector(context.eventBus, objectDescriptorName);
        TableFieldRenderer fieldRenderer =
            new DefaultTableFieldRenderer(
                new AssistedObjectHandlerFactory(context.descStore),
                context.dateFormatter,
                context.numberUtil);
        ineTable = new IneTable(context.descStore, objectDescriptorName, connector, fieldRenderer);
        ineTable.setShowPager(false);
        if (singleselect)
            ineTable.setSelectionBehaviour(SelectionBehaviour.SINGLE_SELECTION);

        ineTable.renderTable();
        initWidget(ineTable.asWidget());
    }

    @Override
    public boolean isReadOnlyWidget() {
        return true;
    }

    @Override
    public boolean handlesList() {
        return true;
    }

    @Override
    public void setListValue(IneList value) {
        this.val = value;

        List<AssistedObject> kvos = new ArrayList<AssistedObject>();

        if (value != null && value.getRelationList() != null) {
            for (Relation r : value.getRelationList()) {
                kvos.add(r.getKvo());
            }
        }

        connector.setDisplayedItems(kvos);
    }

    public IneTable getIneTable() {
        return ineTable;
    }

    @Override
    public IneList getListValue() {
        return val;
    }
}
