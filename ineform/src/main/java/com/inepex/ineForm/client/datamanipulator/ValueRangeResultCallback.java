package com.inepex.ineForm.client.datamanipulator;

import java.util.List;

import com.inepex.ineom.shared.Relation;

public interface ValueRangeResultCallback {
    void onValueRangeResultReady(List<Relation> relationList);
}
