package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.FlowPanelDropController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.IneButtonType;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.validation.ValidationResult;

public class RelationListFW extends DenyingFormWidget {

    public static final String FIXSIZED = "FIXSIZED";

    private class RelationDragHandler extends DragHandlerAdapter {
        @Override
        public void onDragEnd(DragEndEvent event) {
            RelationRow movedObject = (RelationRow) event.getSource();
            int to = relationsPanel.getWidgetIndex(movedObject);
            relationList.move(movedObject.getRelation(), to);
            reRenderRelations();
            fireFormWidgetChanged();
        }
    }

    private class AddButtonClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            relationList.add(new Relation());
            reRenderRelations();
            fireFormWidgetChanged();
        }
    }

    private abstract class PanelClickHandler implements ClickHandler {
        final RelationRow relationRow;

        public PanelClickHandler(RelationRow relationRow) {
            this.relationRow = relationRow;
        }
    }

    private class RemoveClickHandler extends PanelClickHandler {
        public RemoveClickHandler(RelationRow relationRow) {
            super(relationRow);
        }

        @Override
        public void onClick(ClickEvent event) {
            relationList.delete(relationRow.getRelation());
            relationsPanel.remove(relationRow);
            reRenderRelations();
            fireFormWidgetChanged();
        }
    }

    private class UpClickHandler extends PanelClickHandler {
        public UpClickHandler(RelationRow relationRow) {
            super(relationRow);
        }

        @Override
        public void onClick(ClickEvent event) {
            relationList.moveUp(relationRow.getRelation());
            reRenderRelations();
            fireFormWidgetChanged();
        }
    }

    private class DownClickHandler extends PanelClickHandler {
        public DownClickHandler(RelationRow relationRow) {
            super(relationRow);
        }

        @Override
        public void onClick(ClickEvent event) {
            relationList.moveDown(relationRow.getRelation());
            reRenderRelations();
            fireFormWidgetChanged();
        }
    }

    private final FormWidgetChangeHandler fwch = new FormWidgetChangeHandler() {

        @Override
        public void onFormWidgetChange(FormWidgetChangeEvent e) {
            fireFormWidgetChanged();
        }
    };

    private final AbsolutePanel boundaryPanel = new AbsolutePanel();
    private final FlowPanel relationsPanel = new FlowPanel();

    private final List<RelationRow> rowList = new ArrayList<RelationListFW.RelationRow>();

    private final PickupDragController dragConroller = new PickupDragController(
        boundaryPanel,
        false);
    private final FlowPanelDropController dropController = new FlowPanelDropController(
        relationsPanel);

    private final FlowPanel mainPanel = new FlowPanel();
    private final IneButton addButton = new IneButton(IneButtonType.CONTROL, IneFormI18n.ADD());

    private final String relationDescriptorName;
    private final FormContext formCtx;

    private final RelationList relationList;

    private final boolean fixSized;

    /**
     * Creates the RelationListFW. allowOrdering set to true. If you want to
     * disable ordering, you have to call the another constructor explicitly.
     * 
     * @param relationDescriptorName
     * @param valueRangeProvider
     */
    public RelationListFW(
        FormContext formCtx,
        FDesc fielddescriptor,
        String relationDescriptorName,
        boolean fixSized) {
        this(formCtx, fielddescriptor, relationDescriptorName, true, fixSized);
    }

    public RelationListFW(
        FormContext formCtx,
        FDesc fielddescriptor,
        String relationDescriptorName,
        boolean allowOrdering,
        boolean fixSized) {
        super(fielddescriptor);
        this.formCtx = formCtx;
        this.fixSized = fixSized;
        this.relationDescriptorName = relationDescriptorName;
        this.relationList =
            new RelationList(formCtx.descStore, relationDescriptorName, allowOrdering);

        initWidget(mainPanel);

        mainPanel.add(boundaryPanel);
        boundaryPanel.add(relationsPanel);
        mainPanel.add(addButton);

        dragConroller.registerDropController(dropController);
        dragConroller.setBehaviorDragProxy(true);

        dragConroller.addDragHandler(new RelationDragHandler());

        if (fixSized)
            addButton.setVisible(false);
    }

    public void reRenderRelations() {
        relationsPanel.clear();
        rowList.clear();

        for (Relation rel : relationList.getRelations()) {
            RelationRow relationRow = new RelationRow(rel);
            rowList.add(relationRow);
            relationsPanel.add(relationRow);
            relationRow.updateButtonVisiblity();
        }
    }

    @Override
    public boolean handlesList() {
        return true;
    }

    @Override
    protected void onAttach() {
        registerHandler(addButton.addClickHandler(new AddButtonClickHandler()));
        super.onAttach();
    }

    public RelationList getRelationList() {
        return relationList;
    }

    @Override
    public void setListValue(IneList value) {
        if (value == null || value.getRelationList() == null) {
            relationList.setRelations(Collections.<Relation> emptyList());
        } else {
            relationList.setRelations(value.getRelationList());
        }

        reRenderRelations();
    }

    public void clearValidationResult() {
        for (RelationRow row : rowList)
            row.getRelatedForm().dealValidationResult(null);
    }

    public void dealValidationResult(int rowId, ValidationResult vr) {
        rowList.get(rowId).getRelatedForm().dealValidationResult(vr);
    }

    @Override
    public IneList getListValue() {
        IneList list = new IneList();
        list.setRelationList(relationList.getChanges());
        return list;
    }

    class RelationRow extends HandlerAwareComposite {

        HorizontalPanel hp = new HorizontalPanel();

        private final IneForm relatedForm;
        IneButton removeButton = new IneButton(IneButtonType.CONTROL, IneFormI18n.REMOVE());
        IneButton upButton = new IneButton(IneButtonType.CONTROL, IneFormI18n.UP());
        IneButton downButton = new IneButton(IneButtonType.CONTROL, IneFormI18n.DOWN());
        InlineLabel dragLabel = new InlineLabel("Drag");

        private Relation rel;

        private FormWidgetChangeHandler formWidgetChangeHandler = new FormWidgetChangeHandler() {

            @Override
            public void onFormWidgetChange(FormWidgetChangeEvent e) {
                relationList.change(getRelation());
            }
        };

        public RelationRow(Relation rel) {
            initWidget(hp);
            this.rel = rel;

            relatedForm = new IneForm(formCtx, relationDescriptorName, null);
            relatedForm.renderForm();

            if (relationList.isSupportsOrdering())
                hp.add(dragLabel);

            hp.add(relatedForm.asWidget());

            if (!fixSized)
                hp.add(removeButton);

            if (relationList.isSupportsOrdering()) {
                hp.add(upButton);
                hp.add(downButton);
            }

            dragConroller.makeDraggable(this, dragLabel);

            if (rel.getKvo() != null)
                relatedForm.setInitialData(rel.getKvo());
        }

        @Override
        protected void onAttach() {
            registerHandler(removeButton.addClickHandler(new RemoveClickHandler(this)));
            registerHandler(upButton.addClickHandler(new UpClickHandler(this)));
            registerHandler(downButton.addClickHandler(new DownClickHandler(this)));

            for (AbstractFormUnit innerFormUnit : relatedForm.getRootPanelWidget().getFormUnits()) {
                registerHandler(innerFormUnit.addFormWidgetChangeHandler(fwch));
                registerHandler(innerFormUnit.addFormWidgetChangeHandler(formWidgetChangeHandler));
            }

            super.onAttach();
        }

        public IneForm getRelatedForm() {
            return relatedForm;
        }

        public void updateButtonVisiblity() {
            int rowCount = relationList.getRelations().size();
            int index = relationList.getRelations().indexOf(rel);
            upButton.setVisible(index != 0);
            downButton.setVisible(index != rowCount - 1);
        }

        public Relation getRelation() {
            AssistedObject kvo = relatedForm.getValues(relatedForm.getInitialOrEmptyData());
            rel.setKvo(kvo);
            return rel;
        }

    }
}
