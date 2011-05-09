package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatch.SuccessCallback;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class ObjectFinder<T extends AssistedObject> {
	
	KeyValueObject idObject;
	IneDispatch dispatcher;
	Callback<T> callback = null;
		
	public ObjectFinder(String descriptorName, Long id, IneDispatch dispatcher) {
		idObject = new KeyValueObject(descriptorName);
		idObject.setId(id);
		this.dispatcher = dispatcher;
	}
	
	public void executeFind(Callback<T> callback) {
		this.callback = callback;
		ObjectManipulationAction action = new ObjectManipulationAction(ManipulationTypes.REFRESH
				, idObject);
		dispatcher.execute(action, new ObjectRefreshCallback());
	}
	
	private class ObjectRefreshCallback extends SuccessCallback<ObjectManipulationResult> {
		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(ObjectManipulationResult result) {
			callback.onObjectFound((T) result.getObjectsNewState());
		}
	}
	
	/**
	 * @author istvanszoboszlai
	 * ObjectFinder's callback
	 * @param <T>
	 */
	public static interface Callback<T> {
		void onObjectFound(T foundObject);
	}
}
