package com.inepex.ineForm.client.table;

import net.customware.gwt.dispatch.shared.Action;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.PushedActionContext;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.client.pushedevents.PushedEventProvider;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;

/**
 * IneDataConnector, that lists and manipualtes {@link AssistedObject}s. You can
 * specify custom {@link Action}s for list and manipulate actions or just let
 * the class use the default one.
 * 
 * @author istvanszoboszlai
 * 
 */
public class ServerSideDataConnector extends IneDataConnector {

	private final IneDispatch dispatcher;
	private PushedEventProvider pushedEventProvider = null;

	@Inject
	public ServerSideDataConnector(IneDispatch dispatcher, EventBus eventBus, @Assisted String descriptorName) {
		super(eventBus, descriptorName);
		this.dispatcher = dispatcher;
	}

	public void setAssociatedListAction(ObjectListAction associatedListAction) {
		objectList = associatedListAction;
	}

	public void setAssociatedManipulateAction(ObjectManipulationAction associatedManipulateAction) {
		objectManipulation = associatedManipulateAction;
	}

	public ObjectManipulationAction getAssociatedManipulateAction() {
		return (ObjectManipulationAction) objectManipulation;
	}

	public ObjectListAction getAssociatedListAction() {
		return (ObjectListAction) objectList;
	}

	public void setAutoUpdating(PushedEventProvider pEventProvider, HasData<AssistedObject> display) {
		this.pushedEventProvider = pEventProvider;
		createDefaultListActionIfNull();
		pEventProvider.addAction(getAssociatedListAction(), new ObjectRangePushContext(display));
	}

	public void disableAutoUpdating() {
		if (pushedEventProvider != null)
			pushedEventProvider.removeAction(getAssociatedListAction());
	}

	private class ObjectRangePushContext extends PushedActionContext<ObjectListActionResult> {
		HasData<AssistedObject> display;

		public ObjectRangePushContext(HasData<AssistedObject> display) {
			this.display = display;
		}

		@Override
		public void onCastedSuccess(ObjectListActionResult result) {
			updateLastResult(result);
			updateDisplayToLastResult();
		}

		@Override
		public void onBeforeCallAction(Action<?> action) {
			this.customStatusIndicator = customListingStatusIndicator;
			setListActionDetails((ObjectListAction) action, searchParameters, display.getVisibleRange().getStart(), display
					.getVisibleRange()
					.getLength(), false);
		}
	}

	public static interface DataConnectorReadyCallback {

		void ready();

	}

	@Override
	protected ObjectList createNewObjectList() {
		return new ObjectListAction(getDescriptorName());
	}

	@Override
	protected ObjectManipulation createNewObjectManipulate() {
		return new ObjectManipulationAction();
	}

	@Override
	protected void executeManipulation(
			ObjectManipulation objectManipulation,
			final ObjectManipulationCallback manipulationCallback,
			AsyncStatusIndicator statusIndicator) {
		dispatcher.execute((ObjectManipulationAction)objectManipulation, new SuccessCallback<ObjectManipulationActionResult>() {

			@Override
			public void onSuccess(ObjectManipulationActionResult result) {
				manipulationCallback.onSuccess(result);
			}
		}, statusIndicator);

	}

	@Override
	protected void executeObjectList(
			ObjectList objectList,
			final SuccessCallback<ObjectListResult> objectListCallback,
			AsyncStatusIndicator statusIndicator) {
		dispatcher.execute((ObjectListAction)objectList, new SuccessCallback<ObjectListActionResult>() {

			@Override
			public void onSuccess(ObjectListActionResult result) {
				objectListCallback.onSuccess(result);
			}
		}, statusIndicator);

	}
}
