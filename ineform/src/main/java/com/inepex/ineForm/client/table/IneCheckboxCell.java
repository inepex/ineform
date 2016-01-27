package com.inepex.ineForm.client.table;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Event;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;

class IneCheckboxCell extends AbstractCell<Boolean> {

    private IneDataConnector dataConnector;
    private AbstractIneTable ineTable;
    private AssistedObjectHandlerFactory handlerFactory;
    private TableRDesc tableRenderDescriptor;

    public IneCheckboxCell(
        IneDataConnector dataConnector,
        AbstractIneTable ineTable,
        AssistedObjectHandlerFactory handlerFactory,
        TableRDesc tableRenderDescriptor) {
        super("click");
        this.dataConnector = dataConnector;
        this.ineTable = ineTable;
        this.handlerFactory = handlerFactory;
        this.tableRenderDescriptor = tableRenderDescriptor;
    }

    @Override
    public void onBrowserEvent(
        com.google.gwt.cell.client.Cell.Context context,
        Element parent,
        Boolean value,
        NativeEvent event,
        ValueUpdater<Boolean> valueUpdater) {
        int eventType = Event.as(event).getTypeInt();
        if (eventType == Event.ONCLICK) {
            AssistedObject ao = dataConnector.getAssistedObjectByKey((Long) context.getKey());
            if (ineTable.getCheckBoxValueChangeListener() != null && ao != null) {
                List<Node<TableRDescBase>> descriptorNodes = tableRenderDescriptor
                    .getRootNode()
                    .getChildren();
                Node<TableRDescBase> modifiedNode = descriptorNodes.get(context.getColumn());
                AssistedObjectHandler handler = handlerFactory.createHandler(ao);
                if (value == null)
                    value = false;
                handler.set(modifiedNode.getNodeId(), !value);
                ineTable.getCheckBoxValueChangeListener().onCheckBoxValueChanged(
                    ineTable,
                    modifiedNode.getNodeId(),
                    !value,
                    ao);
            }
        }
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
    }

    @Override
    public void render(
        com.google.gwt.cell.client.Cell.Context context,
        Boolean value,
        SafeHtmlBuilder sb) {
        if (Boolean.TRUE.equals(value)) {
            sb.append(SafeHtmlUtils.fromSafeConstant(ineTable.getCheckboxActiveHtml()));
        } else {
            sb.append(SafeHtmlUtils.fromSafeConstant(ineTable.getCheckboxInactiveHtml()));
        }
    }

}
