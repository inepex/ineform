package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface ObjectFinder {
	
	
	void executeFind(String descriptorName, Long id, Callback callback);
	
	void executeFind(String descriptorName, Long id, Callback callback, AsyncStatusIndicator customStatusIndicator);

	/**
	 * @author istvanszoboszlai
	 * ObjectFinder's callback
	 */
	public static interface Callback {
		void onObjectFound(AssistedObject foundObject);
	}
}
