package com.inepex.example.ContactManager.client.page;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.AbstractIneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace.OneParamPresenter;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class MeetingSelectorPage extends ConnectorPage implements OneParamPresenter {

    private SortableIneTable sortableIneTable;

    private final EventBus eventBus;
    private final AuthManager authManager;
    private OneParamPlace oneParamPlace;

    @Inject
    MeetingSelectorPage(
        IneDispatch dispatcher,
        EventBus eventBus,
        AuthManager authManager,
        IneTableFactory ineTableFactory) {
        this.eventBus = eventBus;
        this.authManager = authManager;

        ServerSideDataConnector connector =
            createConnector(dispatcher, eventBus, MeetingConsts.descriptorName);

        sortableIneTable = ineTableFactory.createSortable(
            MeetingConsts.descriptorName,
            connector,
            true);
        sortableIneTable.getFieldRenderer().setCustomFieldRenderer(
            MeetingConsts.k_user,
            new Highlighter());
        sortableIneTable.setSelectionBehaviour(SelectionBehaviour.SINGLE_SELECTION);

        sortableIneTable.renderTable();
        mainPanel.add(sortableIneTable);
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(sortableIneTable.getSelectionModel().addSelectionChangeHandler(
            new SelectionChangeEvent.Handler() {

                @Override
                public void onSelectionChange(SelectionChangeEvent event) {
                    AssistedObject selected =
                        sortableIneTable.getSingleSelectionModel().getSelectedObject();
                    if (selected != null) {
                        sortableIneTable.getSelectionModel().setSelected(selected, false);

                        eventBus.fireEvent(new PlaceRequestEvent(oneParamPlace
                            .getChildPlaceToken(selected.getId().toString())));
                    }
                }
            }));
    }

    private class Highlighter
        implements
        com.inepex.ineForm.shared.render.TableFieldRenderer.CustomCellContentDisplayer {

        @Override
        public String getCustomCellContent(
            AssistedObjectHandler rowKvo,
            String fieldId,
            ColRDesc colRDesc) {
            if (authManager
                .getLastAuthStatusResult()
                .getUserId()
                .equals(rowKvo.getRelation(MeetingConsts.k_user).getId()))
                return "<font style='color:red; font-weight: bold'>me</font>";
            else
                return rowKvo.getRelation(MeetingConsts.k_user).getDisplayName();
        }

    }

    @Override
    public void getDefaultSelection(AsyncCallback<String> callback) {
        callback.onSuccess(null);
    }

    @Override
    public void setSelection(String selected) {}

    @Override
    public void setOneParamPlace(OneParamPlace oneParamPlace) {
        this.oneParamPlace = oneParamPlace;
    }
}
