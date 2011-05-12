package com.inepex.ineForm.client.datamanipulator;

import java.util.List;

import com.inepex.ineom.shared.kvo.Relation;

public interface ValueRangeResultCallback {
	void onValueRangeResultReady(List<Relation> relationList);
}
