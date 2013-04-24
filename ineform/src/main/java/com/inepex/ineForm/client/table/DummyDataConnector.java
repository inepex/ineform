package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.HasData;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;

public class DummyDataConnector extends IneDataConnector{

	private final ObjectListResult dummyResult = new ObjectListResult() {
		
		@Override
		public List<AssistedObject> getList() {
			return new ArrayList<AssistedObject>(DummyDataConnector.this.items);
		}
		
		@Override
		public Long getAllResultCount() {
			return (long) DummyDataConnector.this.items.size();
		}
		
		@Override
		public Boolean isSuccess() {
			return true;
		}
		
		@Override
		public String getMessage() {
			return null;
		}
		
		@Override
		public void setList(List<AssistedObject> list) {
		}
		
		@Override
		public void setAllResultCount(Long allResultCount) {
		}
		
		@Override
		public void setSuccess(Boolean success) {
		}
		
		@Override
		public void setMessage(String message) {
		}

		@Override
		public String getDescriptorName() {
			return null;
		}
	};
	
	private List<? extends AssistedObject> items  = new ArrayList<AssistedObject>();
	
	public DummyDataConnector(EventBus eventBus, String descriptorName) {
		super(eventBus, descriptorName);
	}

	public void startLoading(){
		if (getFirstDataDisplay().getRowCount() == 0) reset();
		getFirstDataDisplay().setVisibleRangeAndClearData(getFirstDataDisplay().getVisibleRange(), false);
	}
	
	public void setDisplayedItems(List<? extends AssistedObject> newItems) {
		if(newItems==null) { 
			this.items  = new ArrayList<AssistedObject>();
		} else {
			this.items = newItems;
		}
		
		update(true);
	}
	
	@Override
	public void update(boolean updateDisplays) {
		if(updateDisplays) {
			updateLastResult(dummyResult);
			
			updateRowCount(items.size(), true);
			
			for(HasData<AssistedObject> d : getDataDisplays()) {
				updateRowData(d, d.getVisibleRange().getStart(), new ArrayList<AssistedObject>(items));
			}
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
