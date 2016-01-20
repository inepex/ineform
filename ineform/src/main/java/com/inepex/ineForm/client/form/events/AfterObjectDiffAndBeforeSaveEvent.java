package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class AfterObjectDiffAndBeforeSaveEvent
        extends FormLifecycleEventBase<AfterObjectDiffAndBeforeSaveEvent.Handler> {

    private static Type<AfterObjectDiffAndBeforeSaveEvent.Handler> TYPE;

    public static interface Handler extends EventHandler {
        void onDiffFinished(AfterObjectDiffAndBeforeSaveEvent event);
    }

    public static Type<AfterObjectDiffAndBeforeSaveEvent.Handler> getType() {
        if (TYPE == null) {
            TYPE = new Type<AfterObjectDiffAndBeforeSaveEvent.Handler>();
        }
        return TYPE;
    }

    private AssistedObject differenceObject;

    public AfterObjectDiffAndBeforeSaveEvent(AssistedObject differenceObject) {
        this.differenceObject = differenceObject;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onDiffFinished(this);
    }

    public AssistedObject getDifferenceObject() {
        return differenceObject;
    }
}