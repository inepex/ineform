package com.inepex.ineForm.client.table;

import net.customware.gwt.dispatch.shared.Action;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.dispatch.ManipulationTypes;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatch.PushedActionContext;
import com.inepex.ineFrame.client.async.IneDispatch.SuccessCallback;
import com.inepex.ineFrame.client.pushedevents.PushedEventProvider;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

/**
 * IneDataConnector, that lists and manipualtes {@link AssistedObject}s.
 * You can specify custom {@link Action}s for list and manipulate actions
 * or just let the class use the default one.
 * 
 * @author istvanszoboszlai
 *
 */
public class ServerSideDataConnector extends IneDataConnector {

	private final IneDispatch dispatcher;

	private ObjectListAction associatedListAction = null;
	private ObjectManipulationAction associatedManipulateAction = null;
	
	private AsyncStatusIndicator customListingStatusIndicator = null;
	private AsyncStatusIndicator customManipulateStatusIndicator = null;
	private PushedEventProvider pushedEventProvider = null;
	
	private boolean isPaging = true;
	
	@Inject
	public ServerSideDataConnector(IneDispatch dispatcher
			, EventBus eventBus
			, @Assisted String descriptorName) {
		super(eventBus, descriptorName);
		this.dispatcher = dispatcher;
	}
	
	public void setCustomListingStatusIndicator(
			AsyncStatusIndicator customListingStatusIndicator) {
		this.customListingStatusIndicator = customListingStatusIndicator;
	}

	public void setCustomManipulateStatusIndicator(
			AsyncStatusIndicator customManipulateStatusIndicator) {
		this.customManipulateStatusIndicator = customManipulateStatusIndicator;
	}

	public void setAssociatedListAction(ObjectListAction associatedListAction) {
		this.associatedListAction = associatedListAction;
	}

	public void setAssociatedManipulateAction(
			ObjectManipulationAction associatedManipulateAction) {
		this.associatedManipulateAction = associatedManipulateAction;
	}
	
	public void setIsPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}
	
	public void setAutoUpdating(PushedEventProvider pEventProvider, HasData<AssistedObject> display) {
		this.pushedEventProvider = pEventProvider;
		createDefaultListActionIfNull();
		pEventProvider.addAction(associatedListAction, new ObjectRangePushContext(display));
	}
	
	public void disableAutoUpdating() {
		if (pushedEventProvider != null)
			pushedEventProvider.removeAction(associatedListAction);
	}

	@Override
	public void objectCreateOrEditRequested(AssistedObject object, ManipulateResultCallback callback) {
		createDefaultManipulateActionIfNUll();
		setManipualteActionDetails(ManipulationTypes.CREATE_OR_EDIT_REQUEST
								 , object);
		sendManipulateAction(associatedManipulateAction, callback);
	}


	@Override
	public void objectDeleteRequested(AssistedObject object, ManipulateResultCallback callback) {
		createDefaultManipulateActionIfNUll();
		setManipualteActionDetails(ManipulationTypes.DELETE
								 , new KeyValueObject(object.getDescriptorName(), object.getId()));
		sendManipulateAction(associatedManipulateAction, callback);
	}

	@Override
	public void objectUnDeleteRequested(AssistedObject object, ManipulateResultCallback callback) {
		// Undelete is not supported by the system yet
	}

	private void sendManipulateAction(
			final ObjectManipulationAction manipulateAction, final ManipulateResultCallback callback) {

		final boolean isCreateRequest = manipulateAction.getObject().isNew();
		dispatcher.execute(manipulateAction,
				new SuccessCallback<ObjectManipulationResult>() {
					@Override
					public void onSuccess(ObjectManipulationResult result) {
						
						if (result.isSuccess()) {
							if (isCreateRequest)
								updateRowCount(lastRowCount + 1, true);
							if (manipulateAction.getManipulationType() == ManipulationTypes.DELETE
									&& lastRowCount > 0)
								updateRowCount(lastRowCount - 1, true);
						}
						if (callback != null)
							callback.onManipulationResult(result);
						updateDisplaysAndfireListChangedEvent();
					}

				}, customManipulateStatusIndicator);
	}

	@Override
	public void update(boolean updateDisplays) {
		createDefaultListActionIfNull();		
		
		if (!isPaging) {
			if (updateDisplays)
				updateDisplaysAndfireListChangedEvent();
			return;
		}
		
		// if table is a paging table than we should query count
		setListActionDetails(associatedListAction, searchParameters, 0, 0 , true);
		dispatcher.execute(associatedListAction, new ObjectListSuccess(updateDisplays)
						 , customListingStatusIndicator);
	}
	
	private class ObjectListSuccess extends SuccessCallback<ObjectListResult> {
		boolean updateDisplays;
		public ObjectListSuccess(boolean updateDisplays) {
			this.updateDisplays = updateDisplays;
		}
		@Override
		public void onSuccess(ObjectListResult result) {
			updateWithObjectListResultCount(result, updateDisplays);
		}
	}
	
	private void updateWithObjectListResultCount(ObjectListResult result, boolean updateDisplays) {
		updateRowCount((int) (null != result ? result.getAllResultCount() : 0), true);
		if (updateDisplays)
			updateDisplaysAndfireListChangedEvent();		
	}
	
	private void createDefaultListActionIfNull() {
		if (associatedListAction == null)
			associatedListAction = new ObjectListAction(getDescriptorName());
		else if (associatedListAction.getDescriptorName() == null)
			associatedListAction.setDescriptorName(getDescriptorName());
	}

	private void setListActionDetails(ObjectListAction action, AssistedObject searchParameters,
			int firstResult, int numMaxResult, boolean queryResultCount) {
		action.setSearchParameters(searchParameters);
		action.setFirstResult(firstResult);
		action.setNumMaxResult(numMaxResult);
		action.setQueryResultCount(queryResultCount);
		action.setOrderKey(orderKey);
		action.setDescending(orderDescending);
	}
	
	private void createDefaultManipulateActionIfNUll() {
		if (associatedManipulateAction == null)
			associatedManipulateAction = new ObjectManipulationAction();
	}
	
	private void setManipualteActionDetails(ManipulationTypes manipulationType, AssistedObject kvo) {
		associatedManipulateAction.setManipulationType(manipulationType);
		associatedManipulateAction.setObject(kvo);
	}

	@Override
	protected void onRangeChanged(HasData<AssistedObject> display) {
		createDefaultListActionIfNull();		
		setListActionDetails(associatedListAction, searchParameters, display.getVisibleRange().getStart(), display
						.getVisibleRange().getLength(), false);

		dispatcher.execute(associatedListAction, new ObjectRangeSuccess(display)
						 , customListingStatusIndicator);
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
	
	private class ObjectRangePushContext extends PushedActionContext<ObjectListResult> {
		HasData<AssistedObject> display;
		public ObjectRangePushContext(HasData<AssistedObject> display) {
			this.display = display;
		}
		@Override
		public void onCastedSuccess(ObjectListResult result) {
			updateSpecificDisplay(result, display);
		}
		@Override
		public void onBeforeCallAction(Action<?> action) {
			this.customStatusIndicator = customListingStatusIndicator;
			setListActionDetails((ObjectListAction)action, searchParameters, display.getVisibleRange().getStart(), display
					.getVisibleRange().getLength(), false);
		}
	}
	
	private void updateSpecificDisplay(ObjectListResult result, HasData<AssistedObject> display) {
		updateRowData(display, display.getVisibleRange().getStart(),
				result.getList());
		if (!isPaging)
			updateRowCount(result.getList().size(), true);
		onNewData(result);			
	}
	
	/**Override this method to be notified about new result of {@link ObjectListAction}
	 * @param objectListResult
	 */
	protected void onNewData(ObjectListResult objectListResult) {
	}
}
