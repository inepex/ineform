package com.inepex.ineForm.client.datamanipulator;

import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
	
public interface ValueRangeProvider {
	
	void getRelationValueRange(FDesc fieldDesc, ValueRangeResultCallback callback);
}
