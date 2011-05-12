package com.inepex.ineForm.client.datamanipulator;

import com.inepex.ineom.shared.descriptor.FDesc;
	
public interface ValueRangeProvider {
	
	void getRelationValueRange(FDesc fieldDesc, ValueRangeResultCallback callback);
}
