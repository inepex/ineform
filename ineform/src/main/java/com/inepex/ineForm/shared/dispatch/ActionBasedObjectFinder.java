package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
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
        executeFind(descriptorName, id, null, callback);
    }

    @Override
    public void executeFind(
        String descriptorName,
        Long id,
        List<String> propGroups,
        Callback callback) {
        executeFind(descriptorName, id, propGroups, callback, null);
    }

    @Override
    public void executeFind(
        String descriptorName,
        Long id,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator) {
        executeFind(descriptorName, descriptorName, id, null, callback, customStatusIndicator);
    }

    @Override
    public void executeFind(
        String descriptorName,
        Long id,
        List<String> propGroups,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator) {
        executeFind(
            descriptorName,
            descriptorName,
            id,
            propGroups,
            callback,
            customStatusIndicator);
    }

    @Override
    public void executeFind(
        String commandDescName,
        String objectDescName,
        Long id,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator) {
        executeFind(commandDescName, objectDescName, id, null, callback, customStatusIndicator);
    }

    @Override
    public void executeFind(
        String commandDescName,
        String objectDescName,
        Long id,
        List<String> propGroups,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator) {
        KeyValueObject idObject = new KeyValueObject(objectDescName);
        idObject.setId(id);
        ObjectManipulationAction action = new ObjectManipulationAction(
            ManipulationTypes.REFRESH,
            idObject);
        if (propGroups != null)
            action.setPropGroups(propGroups.toArray(new String[propGroups.size()]));
        dispatcher.execute(action, new ObjectRefreshCallback(callback), customStatusIndicator);
    }

    private class ObjectRefreshCallback extends SuccessCallback<ObjectManipulationActionResult> {

        private final Callback callback;

        public ObjectRefreshCallback(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(ObjectManipulationActionResult result) {
            callback.onObjectFound(result.getObjectsNewState());
        }
    }

}
