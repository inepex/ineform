package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.HasData;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.kvo.AssistedObject;

public class DummyDataConnector extends IneDataConnector{

	public DummyDataConnector(EventBus eventBus, String descriptorName) {
		super(eventBus, descriptorName);
	}

	public void setDisplayedItems(List<AssistedObject> items) {
		if(items==null) items = new ArrayList<AssistedObject>();
		
		updateRowCount(items.size(), true);
		
		for(HasData<AssistedObject> d : getDataDisplays()) {
			updateRowData(d, d.getVisibleRange().getStart(), items);
		}
	}

	@Override
	protected ObjectList createNewObjectList() {
		return null;
	}

	@Override
	protected ObjectManipulation createNewObjectManipulate() {
		return null;
	}

	@Override
	protected void executeManipulation(
			ObjectManipulation objectManipulation,
			ObjectManipulationCallback manipulationCallback,
			AsyncStatusIndicator statusIndicator) {
	}

	@Override
	protected void executeObjectList(
			ObjectList objectList,
			SuccessCallback<ObjectListResult> objectListCallback,
			AsyncStatusIndicator statusIndicator) {
	}
	

	@Override
	protected void setListActionDetails(ObjectList objectList,
			AssistedObject searchParameters, int firstResult, int numMaxResult,
			boolean queryResultCount) {
	}

}
