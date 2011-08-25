package com.inepex.example.ContactManager.client.page;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.MeetingKVO;
import com.inepex.example.ContactManager.shared.MeetingType;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.IneTable.CustomCellContentDisplayer;
import com.inepex.ineForm.client.table.IneTable.RowStylesProvider;
import com.inepex.ineForm.client.table.IneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineForm.client.util.CETDateProviderCln;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class MeetingSelectorPage extends ConnectorPage {
	
	private SortableIneTable sortableIneTable;
	
	private final EventBus eventBus;
	private final AuthManager authManager;
	
	@Inject
	MeetingSelectorPage(IneDispatch dispatcher, EventBus eventBus,
			DescriptorStore descriptorStore, CETDateProviderCln dateProvider, AuthManager authManager) {
		this.eventBus=eventBus;
		this.authManager = authManager;
		
		ServerSideDataConnector connector = createConnector(dispatcher, eventBus, MeetingKVO.descriptorName);
		
		sortableIneTable = new SortableIneTable(descriptorStore, MeetingKVO.descriptorName, connector);
		sortableIneTable.setDateProvider(dateProvider);
		sortableIneTable.setRowStylesProvider(new HomeStyleProvider());
		sortableIneTable.addCellContentDisplayer(MeetingKVO.k_user, new Highlighter());
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
		public String getCustomCellContent(AssistedObject rowKvo,
				String fieldId, ColRDesc colRDesc) {
			if(authManager.getLastAuthStatusResult().getUserId().equals(rowKvo.getRelation(MeetingKVO.k_user).getId()))
				return "<font style='color:red; font-weight: bold'>me</font>";
			else
				return rowKvo.getRelation(MeetingKVO.k_user).getDisplayName();
		}
		
	}
	
	private class HomeStyleProvider implements RowStylesProvider {

		@Override
		public String getStyleNames(AssistedObject row, int rowIndex) {
			if(MeetingType.INE_OFFICE.equals(row.getEnum(MeetingKVO.k_meetingType, MeetingType.class)))
				return "green-table-line";
			else
				return null;
		}
		
	}
}
