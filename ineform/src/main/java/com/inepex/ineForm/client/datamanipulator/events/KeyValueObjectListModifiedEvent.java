package com.inepex.ineForm.client.datamanipulator.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * TableModel fires this event on change
 *
 */
public class KeyValueObjectListModifiedEvent
        extends GwtEvent<KeyValueObjectListModifiedEventHandler> {

    public static Type<KeyValueObjectListModifiedEventHandler> TYPE = new Type<KeyValueObjectListModifiedEventHandler>();

    /**
     * Type corresponding to the ObjectDescriptor name
     */
    private final String listType;

    public KeyValueObjectListModifiedEvent(String listType) {
        this.listType = listType;
    }

    public String getListType() {
        return listType;
    }

    @Override
    protected void dispatch(KeyValueObjectListModifiedEventHandler handler) {
        handler.onObjectListModified(this);
    }

    @Override
    public GwtEvent.Type<KeyValueObjectListModifiedEventHandler> getAssociatedType() {
        return TYPE;
    }

}
