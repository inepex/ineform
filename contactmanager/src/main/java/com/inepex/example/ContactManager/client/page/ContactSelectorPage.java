package com.inepex.example.ContactManager.client.page;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.IneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace.OneParamPresenter;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class ContactSelectorPage extends ConnectorPage implements OneParamPresenter {
	
	private SortableIneTable sortableIneTable;
	
	private final EventBus eventBus;
	
	private OneParamPlace oneParamPlace;
	
	@Inject
	ContactSelectorPage(IneDispatch dispatcher, EventBus eventBus, IneTableFactory ineTableFactory) {
		this.eventBus=eventBus;
		
		getElement().getStyle().setPadding(0, Unit.PX);
		
		ServerSideDataConnector connector = createConnector(dispatcher, eventBus, ContactConsts.descriptorName);

		sortableIneTable = ineTableFactory.createSortable(ContactConsts.descriptorName, connector);
		
		sortableIneTable.setSelectionBehaviour(SelectionBehaviour.SINGLE_SELECTION);
		
		sortableIneTable.renderTable();
		mainPanel.add(sortableIneTable);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(sortableIneTable.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				AssistedObject selected = sortableIneTable.getSingleSelectionModel().getSelectedObject();
				if(selected!=null) {
					sortableIneTable.getSelectionModel().setSelected(selected, false);
					
					eventBus.fireEvent(
							new PlaceRequestEvent(oneParamPlace.getChildPlaceToken(selected.getId().toString())));
				}
			}
		}));
	}

	@Override
	public void getDefaultSelection(AsyncCallback<String> callback) {
		callback.onSuccess(null);
	}

	@Override
	public void setSelection(String selected) {
	}

	@Override
	public void setOneParamPlace(OneParamPlace oneParamPlace) {
		this.oneParamPlace = oneParamPlace;
	}
	
}
