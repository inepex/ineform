package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface ObjectFinder {

    void executeFind(String descriptorName, Long id, Callback callback);

    void executeFind(String descriptorName, Long id, List<String> propGroups, Callback callback);

    void executeFind(
        String descriptorName,
        Long id,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator);

    void executeFind(
        String descriptorName,
        Long id,
        List<String> propGroups,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator);

    void executeFind(
        String commandDescName,
        String objectDescName,
        Long id,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator);

    void executeFind(
        String commandDescName,
        String objectDescName,
        Long id,
        List<String> propGroups,
        Callback callback,
        AsyncStatusIndicator customStatusIndicator);

    /**
     * @author istvanszoboszlai ObjectFinder's callback
     */
    public static interface Callback {
        void onObjectFound(AssistedObject foundObject);
    }
}
