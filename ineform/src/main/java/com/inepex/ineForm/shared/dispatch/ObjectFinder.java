package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;

public class ObjectFinder {
	
	KeyValueObject idObject;
	IneDispatch dispatcher;
	Callback callback = null;
		
	public ObjectFinder(String descriptorName, Long id, IneDispatch dispatcher) {
		idObject = new KeyValueObject(descriptorName);
		idObject.setId(id);
		this.dispatcher = dispatcher;
	}
	
	public void executeFind(Callback callback) {
		this.callback = callback;
		ObjectManipulationAction action = new ObjectManipulationAction(ManipulationTypes.REFRESH
				, idObject);
		dispatcher.execute(action, new ObjectRefreshCallback());
	}
	
	private class ObjectRefreshCallback extends SuccessCallback<ObjectManipulationActionResult> {
		@Override
		public void onSuccess(ObjectManipulationActionResult result) {
			callback.onObjectFound(result.getObjectsNewState());
		}
	}
	
	/**
	 * @author istvanszoboszlai
	 * ObjectFinder's callback
	 */
	public static interface Callback {
		void onObjectFound(AssistedObject foundObject);
	}
}
