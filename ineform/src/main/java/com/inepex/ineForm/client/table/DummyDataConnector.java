package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.HasData;
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
	public void objectCreateOrEditRequested(AssistedObject object,
			ManipulateResultCallback callback) {
	}

	@Override
	public void objectDeleteRequested(AssistedObject object,
			ManipulateResultCallback callback) {
	}

	@Override
	public void objectUnDeleteRequested(AssistedObject object,
			ManipulateResultCallback callback) {
	}

	@Override
	protected void onRangeChanged(HasData<AssistedObject> display) {
		
	}

	@Override
	public void update(boolean updateDisplays) {
	}

}
