package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface ObjectFinder {
	
	
	void executeFind(String descriptorName, Long id, Callback callback);

	/**
	 * @author istvanszoboszlai
	 * ObjectFinder's callback
	 */
	public static interface Callback {
		void onObjectFound(AssistedObject foundObject);
	}
}
