package com.inepex.example.ContactManager.client.page;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.IneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class CompanySelectorPage extends ConnectorPage {
	
	private SortableIneTable sortableIneTable;
	
	private final EventBus eventBus;
	
	@Inject
	CompanySelectorPage(IneDispatch dispatcher, EventBus eventBus, DescriptorStore descriptorStore) {
		this.eventBus=eventBus;
		
		ServerSideDataConnector connector = createConnector(dispatcher, eventBus, CompanyConsts.descriptorName);
		
		sortableIneTable = new SortableIneTable(descriptorStore, CompanyConsts.descriptorName, connector);
		
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
							new PlaceRequestEvent(
								PlaceHandlerHelper.appendParam(
										currentPlace.getHierarchicalToken(),
										AppPlaceHierarchyProvider.PARAM_COMPANY,
										selected.getId().toString())));
				}
			}
		}));
	}
	
}
