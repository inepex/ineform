package com.inepex.ineForm.client.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.BatchAction;
import net.customware.gwt.dispatch.shared.BatchAction.OnException;
import net.customware.gwt.dispatch.shared.BatchResult;

import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;

public class CachingValueRangeProvider extends ServerSideValueRangeProvider {

    final Map<String, List<Relation>> cachedRelationLists = new HashMap<String, List<Relation>>();
    final boolean useDefaultProviderIfNotCached;
    private Map<String, ArrayList<ValueRangeResultCallback>> waitingCalls = new HashMap<String, ArrayList<ValueRangeResultCallback>>();

    public CachingValueRangeProvider(
        IneDispatch dispatch,
        boolean useDefaultProviderIfNotCached,
        String... cachedRelationDescriptorNames) {
        super(dispatch);
        this.useDefaultProviderIfNotCached = useDefaultProviderIfNotCached;
        for (String string : cachedRelationDescriptorNames) {
            cachedRelationLists.put(string, null);
        }
    }

    @Override
    public void getRelationValueRange(FDesc fieldDesc, ValueRangeResultCallback callback) {

        RelationFDesc relFieldDesc = super.castDescriptorCheckType(fieldDesc, callback);
        if (relFieldDesc == null)
            return;

        String descriptorName = relFieldDesc.getRelatedDescriptorName();

        if (cachedRelationLists.containsKey(descriptorName)
            && cachedRelationLists.get(descriptorName) != null) {
            callback.onValueRangeResultReady(cachedRelationLists.get(descriptorName));
        } else if (!useDefaultProviderIfNotCached) {
            addToWaitingCalls(descriptorName, callback);
        } else if (useDefaultProviderIfNotCached)
            super.getRelationValueRange(relFieldDesc, callback);

    }

    public void addCachedType(String objectDescriptorName) {
        cachedRelationLists.put(objectDescriptorName, null);
    }

    public void fetchChanges() {

        Action<?>[] actions = new Action<?>[cachedRelationLists.keySet().size()];

        int i = 0;
        for (String descriptorName : cachedRelationLists.keySet()) {
            actions[i++] = new RelationListAction(descriptorName, null, 0, 1000, false);
        }

        BatchAction batchAction = new BatchAction(OnException.CONTINUE, actions);

        dispatch.execute(batchAction, new RelationListResultsCallback(actions));
    }

    class RelationListResultsCallback extends SuccessCallback<BatchResult> {

        Action<?>[] actions;

        public RelationListResultsCallback(Action<?>[] actions) {
            super();
            this.actions = actions;
        }

        @Override
        public void onSuccess(BatchResult result) {
            for (int i = 0; i < actions.length; i++) {
                RelationListActionResult relListResult = (RelationListActionResult) result
                    .getResult(i);
                String descriptorName = ((RelationListAction) actions[i]).getDescriptorName();
                cachedRelationLists.put(descriptorName, relListResult.getList());
                if (waitingCalls.get(descriptorName) != null) {
                    for (ValueRangeResultCallback callback : waitingCalls.get(descriptorName)) {
                        callback.onValueRangeResultReady(relListResult.getList());
                    }
                    waitingCalls.get(descriptorName).clear();
                }
            }

        }
    }

    private void addToWaitingCalls(String descriptorName, ValueRangeResultCallback callback) {
        if (waitingCalls.get(descriptorName) == null)
            waitingCalls.put(descriptorName, new ArrayList<ValueRangeResultCallback>());
        waitingCalls.get(descriptorName).add(callback);
    }

}
