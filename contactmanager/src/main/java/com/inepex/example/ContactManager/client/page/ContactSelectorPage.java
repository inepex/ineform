package com.inepex.example.ContactManager.client.page;

import java.util.Arrays;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.AbstractIneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.render.TableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace.OneParamPresenter;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class ContactSelectorPage extends ConnectorPage implements OneParamPresenter {

    private SortableIneTable sortableIneTable;

    private final EventBus eventBus;

    private OneParamPlace oneParamPlace;

    @Inject
    ContactSelectorPage(IneDispatch dispatcher, EventBus eventBus, IneTableFactory ineTableFactory) {
        this.eventBus = eventBus;

        getElement().getStyle().setPadding(0, Unit.PX);

        ServerSideDataConnector connector =
            createConnector(dispatcher, eventBus, ContactConsts.descriptorName);
        connector.setAssociatedListAction(new ObjectListAction(ContactConsts.descriptorName, Arrays
            .asList(ContactConsts.props_user)));

        sortableIneTable = ineTableFactory.createSortable(
            ContactConsts.descriptorName,
            connector,
            true);

        sortableIneTable.setSelectionBehaviour(SelectionBehaviour.SINGLE_SELECTION);

        sortableIneTable.getFieldRenderer().setCustomFieldRenderer(
            ContactConsts.k_note,
            new CustomCellContentDisplayer() {

                @Override
                public String getCustomCellContent(
                    AssistedObjectHandler rowKvo,
                    String fieldId,
                    ColRDesc colRDesc) {
                    String userProps =
                        rowKvo.getAssistedObject().getPropsJson(ContactConsts.props_user);
                    if (userProps != null) {
                        JSONObject userPropsJson = JSONParser.parseStrict(userProps).isObject();
                        if (userPropsJson != null) {
                            if (userPropsJson.containsKey(ContactConsts.k_note)) {
                                return userPropsJson
                                    .get(ContactConsts.k_note)
                                    .isString()
                                    .stringValue();
                            }
                        }
                    }
                    return "";
                }
            });

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
