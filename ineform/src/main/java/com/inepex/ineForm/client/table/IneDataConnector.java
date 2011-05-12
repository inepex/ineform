package com.inepex.ineForm.client.table;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineom.shared.kvo.AssistedObject;

public abstract class IneDataConnector extends
		AsyncDataProvider<AssistedObject> {

	
	
	private final String descriptorName;
	protected final EventBus eventBus;

	protected boolean suppotsIsDeleted = false;
	protected boolean showDeletedActive = false;

	protected int lastRowCount = -1;
	protected String orderKey = null;
	protected boolean orderDescending = false;

	protected AssistedObject searchParameters;

	public IneDataConnector(EventBus eventBus, String descriptorName) {
		this.eventBus = eventBus;
		this.descriptorName = descriptorName;

	}

	public String getDescriptorName() {
		return descriptorName;
	}
	
	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public boolean isOrderDescending() {
		return orderDescending;
	}

	public void setOrderDescending(boolean orderDescending) {
		this.orderDescending = orderDescending;
	}

	public static interface ManipulateResultCallback {
		void onManipulationResult(ObjectManipulationResult result);
	}
	
	@Override
	public void updateRowCount(int size, boolean exact) {
		this.lastRowCount = size;
		super.updateRowCount(size, exact);
	}

	/**
	 * This function is called when a new object is created
	 * 
	 * @param object
	 */
	public abstract void objectCreateOrEditRequested(AssistedObject object, ManipulateResultCallback callback);

	/**
	 * This function is called when an object is deleted
	 * 
	 * @param object
	 */
	public abstract void objectDeleteRequested(AssistedObject object, ManipulateResultCallback callback);

	/**
	 * This function is called when an object is deleted
	 * 
	 * @param object
	 */
	public abstract void objectUnDeleteRequested(AssistedObject object, ManipulateResultCallback callback);
	
	protected void updateDisplaysAndfireListChangedEvent() {
		for (HasData<AssistedObject> display : getDataDisplays()) {
			onRangeChanged(display);
		}
		eventBus.fireEvent(new KeyValueObjectListModifiedEvent(descriptorName));
	}

	protected boolean isShowDeletedActive() {
		return showDeletedActive;
	}

	public void setSearchParametersAndUpdate(AssistedObject searchParameters) {
		this.searchParameters = searchParameters;
		
		boolean updateDisplays = lastRowCount != -1;
		// we should only update the displays if the rowCount have already been set at least once
		update(updateDisplays);  
	}

	public abstract void update(boolean updateDisplays);
	
}
