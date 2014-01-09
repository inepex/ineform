package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.inepex.ineForm.client.datamanipulator.events.KeyValueObjectListModifiedEvent;
import com.inepex.ineForm.client.table.ServerSideDataConnector.DataConnectorReadyCallback;
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

	public class ObjectManipulationCallback extends SuccessCallback<ObjectManipulationResult> {

		private final ObjectManipulation currentManipulation;
		private final ManipulateResultCallback currentCallback;

		private ObjectManipulationCallback(ObjectManipulation currentManipulation, ManipulateResultCallback currentCallback) {
			this.currentManipulation = currentManipulation;
			this.currentCallback = currentCallback;
		}

		@Override
		public void onSuccess(ObjectManipulationResult result) {
			if (result!=null && result.isSuccess() && (result.getValidationResult()==null || result.getValidationResult().isValid())) {
				if (currentManipulation.getObject()!=null
						&& result.getObjectsNewState()!=null){
					if (currentManipulation.getObject().isNew()){
						rowCount++;
						resultMap.put(result.getObjectsNewState().getId(), resultList.size());
						resultList.add(result.getObjectsNewState());
					} else {
						//case of its not a form
						if(resultMap.get(result.getObjectsNewState().getId())!=null) {
							resultList.set(resultMap.get(result.getObjectsNewState().getId()), result.getObjectsNewState());
						}
					}
				}
				
				if (currentManipulation.getManipulationType() == ManipulationTypes.DELETE && rowCount > 0){
					--rowCount;
					int indexOfDeleted = resultMap.get(currentManipulation.getObject().getId()).intValue();
					resultList.remove(indexOfDeleted);
					resultMap.remove(currentManipulation.getObject().getId());
					for(Entry<Long, Integer> entry : resultMap.entrySet()) {
						if(entry.getValue()>=indexOfDeleted)
							entry.setValue(entry.getValue()-1);
					}
				}
			}
			if (currentCallback != null)
				currentCallback.onManipulationResult(result);

			updateDisplaysAndfireListChangedEvent();
		}

	}

	private final String descriptorName;
	private final EventBus eventBus;

	private String orderKey = null;
	private boolean orderDescending = false;

	protected AssistedObject searchParameters;
	
	/**
	 * map assisted object id to list index
	 */
	private HashMap<Long, Integer> resultMap = new HashMap<Long, Integer>();
	
	private List<AssistedObject> resultList = new ArrayList<AssistedObject>();
	
	private Long rowCount = new Long(0L);
	
	protected ObjectList objectList = null;
	protected ObjectManipulation objectManipulation = null;

	protected AsyncStatusIndicator customListingStatusIndicator = null;
	private AsyncStatusIndicator customManipulateStatusIndicator = null;
	
	private boolean isPaging = true;
	
	private DataConnectorReadyCallback callback;	
	
	private boolean firstCall = true;

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

	private void createDefaultManipulateActionIfNUll() {
		if (objectManipulation == null)
			objectManipulation = createNewObjectManipulate();
		// new ObjectManipulationAction();
	}

	private void setManipulateActionDetails(
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

	@Override
	protected void onRangeChanged(HasData<AssistedObject> display) {
		update();
	}
	
	public List<AssistedObject> getResultList(){
		return resultList;
	}
	
	public AssistedObject getAssistedObjectByKey(Long key){
		return resultList.get(resultMap.get(key));
	}
	
	public Integer getSeqNumberById(Long key) {
		return resultMap.get(key);
	}
	
	protected HasData<AssistedObject> getFirstDataDisplay(){
		if (getDataDisplays() != null && getDataDisplays().size() > 0){
			return getDataDisplays().iterator().next();
		} else {
			throw new RuntimeException("DataDisplay not set on IneDataConnector. Call IneTable.renderTable() before updating dataConnector");
		}
	}
	
	public void update(boolean dummy){
		update();
	}
	
	public void update(boolean dummy, DataConnectorReadyCallback callback){
		update(callback);
	}
	
	public void update(){
		update(null);
	}
	
	public void update(DataConnectorReadyCallback callback){
		this.callback = callback;
		if (getFirstDataDisplay().getRowCount() == 0) reset();
		getFirstDataDisplay().setVisibleRangeAndClearData(getFirstDataDisplay().getVisibleRange(), false);
		if (!firstCall){
			createDefaultListActionIfNull();
			setListActionDetails(objectList, searchParameters, getFirstDataDisplay().getVisibleRange().getStart(), 
					getFirstDataDisplay().getVisibleRange().getLength(), isPaging);
			executeObjectList(objectList, new ObjectRangeSuccess(), customListingStatusIndicator);
		}
	}
	
	public void setSearchParameters(AssistedObject searchParameters){
		this.searchParameters = searchParameters;
	}
		
	public AssistedObject getSearchParameters() {
		return searchParameters;
	}

	private void updateDisplaysAndfireListChangedEvent() {
		updateDisplayToLastResult();
		eventBus.fireEvent(new KeyValueObjectListModifiedEvent(descriptorName));
	}
	
	@Override
	public void addDataDisplay(final HasData<AssistedObject> display) {
		super.addDataDisplay(display);
		firstCall = false;
	}
	
	protected void reset(){
		getFirstDataDisplay().setRowCount(1);
	}
	
	protected void updateLastResult(ObjectListResult result){
		rowCount = result.getAllResultCount();
		resultList.clear();
		resultMap.clear();
		if(result.getList()!=null) {
			for(AssistedObject obj : result.getList()){
				resultMap.put(obj.getId(), resultList.size());
				resultList.add(obj);			
			}
		}
	}
	
	protected void updateDisplayToLastResult() {
		if (getDataDisplays() != null && getDataDisplays().size() > 0){
			if (isPaging) {
				getFirstDataDisplay().setRowCount(rowCount.intValue());
				updateRowCount(rowCount.intValue(), true);
			}
			updateRowData(getFirstDataDisplay(), getFirstDataDisplay().getVisibleRange().getStart(), 
					resultList);
		}
	}
	
	private class ObjectRangeSuccess extends SuccessCallback<ObjectListResult> {

		@Override
		public void onSuccess(ObjectListResult result) {	
			if(result.isSuccess()){
				setIdsIfNotSet(result);
				updateLastResult(result);			
				updateDisplayToLastResult();
			}
			
			//TODO: rethink the use of statusindicator
			if (!result.isSuccess() && customListingStatusIndicator != null){
				customListingStatusIndicator.onGeneralFailure("");
			}
			
			if (callback != null)
				callback.ready();
		}
		
		/**
		 * without ids, table commands won't work
		 */
		private void setIdsIfNotSet(ObjectListResult result){
			if (result.getList() != null) {
				long counter = 0L;
				for (AssistedObject ao : result.getList()){
					if (ao.getId() == null) {
						ao.setId(counter++);
					}
				}
			}
		}
		
		
	}

}
