package com.inepex.example.ContactManager.client.page;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.IneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class MeetingSelectorPage extends ConnectorPage {
	
	private SortableIneTable sortableIneTable;
	
	private final EventBus eventBus;
	private final AuthManager authManager;
	
	@Inject
	MeetingSelectorPage(IneDispatch dispatcher, EventBus eventBus,
			DescriptorStore descriptorStore, AuthManager authManager,
			AssistedObjectTableFieldRenderer fieldRenderer) {
		this.eventBus=eventBus;
		this.authManager = authManager;
		
		ServerSideDataConnector connector = createConnector(dispatcher, eventBus, MeetingConsts.descriptorName);
		
		sortableIneTable = new SortableIneTable(descriptorStore, MeetingConsts.descriptorName, connector,
				fieldRenderer);
		sortableIneTable.addCellContentDisplayer(MeetingConsts.k_user, new Highlighter());
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
										AppPlaceHierarchyProvider.PARAM_MEETING,
										selected.getId().toString())));
				}
			}
		}));
	}
	
	private class Highlighter implements CustomCellContentDisplayer {

		@Override
		public String getCustomCellContent(AssistedObjectHandler rowKvo,
				String fieldId, ColRDesc colRDesc) {
			if(authManager.getLastAuthStatusResult().getUserId().equals(rowKvo.getRelation(MeetingConsts.k_user).getId()))
				return "<font style='color:red; font-weight: bold'>me</font>";
			else
				return rowKvo.getRelation(MeetingConsts.k_user).getDisplayName();
		}
		
	}
}
