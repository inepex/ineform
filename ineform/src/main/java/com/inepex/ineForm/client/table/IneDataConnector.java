package com.inepex.ineForm.client.table;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.client.table.ServerSideDataConnector.DataConnectorReadyCallback;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;

public abstract class IneDataConnector extends AsyncDataProvider<AssistedObject> {

	protected class ObjectManipulationCallback extends SuccessCallback<ObjectManipulationResult> {

		ObjectManipulation objectManipulation;
		boolean isCreateRequest;
		ManipulateResultCallback callback;

		public ObjectManipulationCallback(ObjectManipulation objectManipulation, ManipulateResultCallback callback) {
			super();
			this.objectManipulation = objectManipulation;
			this.isCreateRequest = objectManipulation.getObject().isNew();
			this.callback = callback;
		}

		@Override
		public void onSuccess(ObjectManipulationResult result) {
			if (result.isSuccess()) {
				if (isCreateRequest)
					updateRowCount(lastRowCount + 1, true);
				if (objectManipulation.getManipulationType() == ManipulationTypes.DELETE && lastRowCount > 0)
					updateRowCount(lastRowCount - 1, true);
			}
			if (callback != null)
				callback.onManipulationResult(result);

			updateDisplaysAndfireListChangedEvent();
		}

	}

	private class ObjectListSuccess extends SuccessCallback<ObjectListResult> {
		private final boolean updateDisplays;
		private final DataConnectorReadyCallback readyCallback;

		public ObjectListSuccess(boolean updateDisplays, DataConnectorReadyCallback readyCallback) {
			this.updateDisplays = updateDisplays;
			this.readyCallback = readyCallback;
		}

		@Override
		public void onSuccess(ObjectListResult result) {
			updateWithObjectListResultCount(result, updateDisplays);

			if (readyCallback != null)
				readyCallback.ready();
		}
	}

	private class ObjectRangeSuccess extends SuccessCallback<ObjectListResult> {
		HasData<AssistedObject> display;

		public ObjectRangeSuccess(HasData<AssistedObject> display) {
			this.display = display;
		}

		@Override
		public void onSuccess(ObjectListResult result) {
			updateSpecificDisplay(result, display);
		}
	}

	private final String descriptorName;
	protected final EventBus eventBus;

	protected boolean suppotsIsDeleted = false;
	protected boolean showDeletedActive = false;

	protected int lastRowCount = -1;
	protected String orderKey = null;
	protected boolean orderDescending = false;

	protected AssistedObject searchParameters;

	protected ObjectList objectList = null;
	protected ObjectManipulation objectManipulation = null;

	protected AsyncStatusIndicator customListingStatusIndicator = null;
	protected AsyncStatusIndicator customManipulateStatusIndicator = null;

	private boolean isPaging = true;

	public IneDataConnector(EventBus eventBus, String descriptorName) {
		this.eventBus = eventBus;
		this.descriptorName = descriptorName;

	}

	protected abstract ObjectList createNewObjectList();

	protected abstract ObjectManipulation createNewObjectManipulate();

	protected abstract void executeManipulation(
			ObjectManipulation objectManipulation,
			ObjectManipulationCallback manipulationCallback,
			AsyncStatusIndicator statusIndicator);

	protected abstract void executeObjectList(
			ObjectList objectList,
			SuccessCallback<ObjectListResult> objectListCallback,
			AsyncStatusIndicator statusIndicator);

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

	public void setCustomListingStatusIndicator(AsyncStatusIndicator customListingStatusIndicator) {
		this.customListingStatusIndicator = customListingStatusIndicator;
	}

	public void setCustomManipulateStatusIndicator(AsyncStatusIndicator customManipulateStatusIndicator) {
		this.customManipulateStatusIndicator = customManipulateStatusIndicator;
	}

	public void setIsPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public static interface ManipulateResultCallback {
		void onManipulationResult(ObjectManipulationResult result);
	}

	@Override
	public void updateRowCount(int size, boolean exact) {
		this.lastRowCount = size;
		super.updateRowCount(size, exact);
	}

	protected void createDefaultListActionIfNull() {
		if (objectList == null)
			objectList = createNewObjectList();
		// new ObjectListAction(getDescriptorName());
		else if (objectList.getDescriptorName() == null)
			objectList.setDescriptorName(getDescriptorName());
	}

	protected void setListActionDetails(
			ObjectList objectList,
			AssistedObject searchParameters,
			int firstResult,
			int numMaxResult,
			boolean queryResultCount) {
		objectList.setSearchParameters(searchParameters);
		objectList.setFirstResult(firstResult);
		objectList.setNumMaxResult(numMaxResult);
		objectList.setQueryResultCount(queryResultCount);
		objectList.setOrderKey(orderKey);
		objectList.setDescending(orderDescending);
	}

	protected void createDefaultManipulateActionIfNUll() {
		if (objectManipulation == null)
			objectManipulation = createNewObjectManipulate();
		// new ObjectManipulationAction();
	}

	protected void setManipulateActionDetails(
			ObjectManipulation objectManipulation,
			ManipulationTypes manipulationType,
			AssistedObject kvo, CustomKVOObjectDesc... customObjectDescs) {
		objectManipulation.setManipulationType(manipulationType);
		objectManipulation.setObject(kvo);
		objectManipulation.setCustomOdescs(customObjectDescs);
	}

	/**
	 * This function is called when a new object is created
	 * 
	 * @param object
	 */
	public void objectCreateOrEditRequested(AssistedObject object, ManipulateResultCallback callback, CustomKVOObjectDesc... customObjectDescs ) {
		createDefaultManipulateActionIfNUll();
		setManipulateActionDetails(objectManipulation, ManipulationTypes.CREATE_OR_EDIT_REQUEST, object, customObjectDescs);
		executeManipulation(
				objectManipulation,
				new ObjectManipulationCallback(objectManipulation, callback),
				customManipulateStatusIndicator);
	}

	/**
	 * This function is called when an object is deleted
	 * 
	 * @param object
	 */
	public void objectDeleteRequested(AssistedObject object, ManipulateResultCallback callback) {
		createDefaultManipulateActionIfNUll();
		setManipulateActionDetails(objectManipulation, ManipulationTypes.DELETE, new KeyValueObject(
				object.getDescriptorName(),
				object.getId()));
		executeManipulation(
				objectManipulation,
				new ObjectManipulationCallback(objectManipulation, callback),
				customManipulateStatusIndicator);
	}

	/**
	 * This function is called when an object is deleted
	 * 
	 * @param object
	 */
	public void objectUnDeleteRequested(AssistedObject object, ManipulateResultCallback callback) {

	}

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
		// we should only update the displays if the rowCount have already been
		// set at least once
		update(updateDisplays);
	}

	public void update(boolean updateDisplays) {
		update(updateDisplays, null);
	}

	public void update(boolean updateDisplays, DataConnectorReadyCallback callback) {
		createDefaultListActionIfNull();

		if (!isPaging) {
			if (updateDisplays)
				updateDisplaysAndfireListChangedEvent();
			return;
		}

		// if table is a paging table than we should query count
		setListActionDetails(objectList, searchParameters, 0, 0, true);
		executeObjectList(objectList, new ObjectListSuccess(updateDisplays, callback), customListingStatusIndicator);
	}

	private void updateWithObjectListResultCount(ObjectListResult result, boolean updateDisplays) {
		updateRowCount((int) (null != result ? result.getAllResultCount() : 0), true);
		if (updateDisplays)
			updateDisplaysAndfireListChangedEvent();
	}

	@Override
	protected void onRangeChanged(HasData<AssistedObject> display) {
		createDefaultListActionIfNull();
		setListActionDetails(objectList, searchParameters, display.getVisibleRange().getStart(), display
				.getVisibleRange()
				.getLength(), false);

		executeObjectList(objectList, new ObjectRangeSuccess(display), customListingStatusIndicator);
	}

	protected void updateSpecificDisplay(ObjectListResult result, HasData<AssistedObject> display) {
		updateRowData(display, display.getVisibleRange().getStart(), result.getList());
		if (!isPaging)
			updateRowCount(result.getList().size(), true);
		onNewData(result);
	}

	/**
	 * Override this method to be notified about new result of
	 * {@link ObjectListAction}
	 * 
	 * @param objectListResult
	 */
	protected void onNewData(ObjectListResult objectListResult) {
	}
}
