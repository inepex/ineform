package com.inepex.ineForm.client.datamanipulator.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author istvan
 *
 */
public interface KeyValueObjectListModifiedEventHandler extends EventHandler {
    void onObjectListModified(KeyValueObjectListModifiedEvent event);
}
