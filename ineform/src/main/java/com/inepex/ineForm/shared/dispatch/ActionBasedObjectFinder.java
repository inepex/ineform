package com.inepex.ineForm.shared.dispatch;

import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;

public class ActionBasedObjectFinder implements ObjectFinder {
	
	private final IneDispatch dispatcher;
		
	@Inject
	public ActionBasedObjectFinder(IneDispatch dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	@Override
	public void executeFind(String descriptorName, Long id, Callback callback) {
		KeyValueObject idObject = new KeyValueObject(descriptorName);
		idObject.setId(id);
		ObjectManipulationAction action = new ObjectManipulationAction(ManipulationTypes.REFRESH, idObject);
		dispatcher.execute(action, new ObjectRefreshCallback(callback));
	}
	
	private class ObjectRefreshCallback extends SuccessCallback<ObjectManipulationActionResult> {
		
		private final Callback callback;
		
		public ObjectRefreshCallback(Callback callback) {
			this.callback=callback;
		}

		@Override
		public void onSuccess(ObjectManipulationActionResult result) {
			callback.onObjectFound(result.getObjectsNewState());
		}
	}
}