package com.inepex.ineForm.client.datamanipulator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.Color;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.AbstractIneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class SingleSelectDataManipulator extends DataManipulator {
    private final RightSideButtonsPanel rightSideButtonsPanel = new RightSideButtonsPanel();

    private final IneButton newButton = new IneButton(Color.GREEN, IneFormI18n.NEW());
    private final IneButton editButton = new IneButton(Color.GRAY, IneFormI18n.EDIT());
    private final IneButton deleteButton = new IneButton(Color.GREEN, IneFormI18n.DELETE());

    @Inject
    public SingleSelectDataManipulator(
        FormContext formCtx,
        FormFactory formFactory,
        @Assisted String objectDescriptorName,
        @Assisted IneDataConnector ineDataConnector,
        @Assisted boolean sortable,
        TableFieldRenderer fieldRenderer) {
        super(
            formCtx,
            formFactory,
            objectDescriptorName,
            ineDataConnector,
            sortable,
            fieldRenderer);

    }

    @Override
    protected void initilaizeIneTableAndBuildCustom() {
        ineTable.setSelectionBehaviour(SelectionBehaviour.SINGLE_SELECTION);
        mainPanel.add(rightSideButtonsPanel);
        rightSideButtonsPanel.getCenterPanel().add(ineTable);
        rightSideButtonsPanel.getButtonPanel().add(newButton);
        rightSideButtonsPanel.getButtonPanel().add(editButton);
        rightSideButtonsPanel.getButtonPanel().add(deleteButton);
    }

    @Override
    protected void onAttach() {
        setButtonEnabledStates();
        registerHandler(newButton.addClickHandler(new AddDataClickHandler()));
        registerHandler(editButton.addClickHandler(new EditDataClickHandler()));
        registerHandler(deleteButton.addClickHandler(new DeleteDataClickHandler()));
        registerHandler(
            ineTable
                .getSelectionModel()
                .addSelectionChangeHandler(new RowSelectionChangeHandler()));
        super.onAttach();
    }

    /**
     * Sets buttons enabled states according to selection state, and
     * isShowDeletedActive() state
     */
    protected void setButtonEnabledStates() {
        editButton.setEnabled(isAnyRowSelected());
        deleteButton.setEnabled(isAnyRowSelected());
    }

    /**
     * Handles change in selected rows of IneTable. Now IneTable is used with
     * single selection
     * 
     * @author István Szoboszlai
     */
    private class RowSelectionChangeHandler implements SelectionChangeEvent.Handler {
        @Override
        public void onSelectionChange(SelectionChangeEvent event) {
            setButtonEnabledStates();
            onRowSelected(getSelectedRow());
        }

    }

    /**
     * You can use it for being notified about row selection. This is handy, if
     * you want to use custom events
     * 
     * @param list
     *            List of the selected rows
     */
    public void onRowSelected(AssistedObject row) {

    }

    protected class AddDataClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            onAddDataClicked();
        }
    }

    protected class EditDataClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            if (isAnyRowSelected()) {
                onEditClicked(getSelectedRow());
            }
        }
    }

    protected class DeleteDataClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            if (isAnyRowSelected()) {
                onDeleteClicked(getSelectedRow());
            }
        }
    }

    protected AssistedObject getSelectedRow() {
        return ineTable.getSingleSelectionModel().getSelectedObject();
    }

    protected boolean isAnyRowSelected() {
        return getSelectedRow() != null;
    }

    @Override
    public void setNewText(String newText) {
        newButton.setText(newText);

    }

    @Override
    public void setEditText(String editText) {
        editButton.setText(editText);
    }

    @Override
    public void setDeleteText(String deleteText) {
        deleteButton.setText(deleteText);
    }

}
