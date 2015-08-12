package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;

public class CancelledEvent extends FormLifecycleEventBase<CancelledEvent.Handler> {

    private static Type<CancelledEvent.Handler> TYPE;

    public static interface Handler extends EventHandler {
        void onCancelled(CancelledEvent event);
    }

    public static Type<CancelledEvent.Handler> getType() {
        if (TYPE == null) {
            TYPE = new Type<CancelledEvent.Handler>();
        }
        return TYPE;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onCancelled(this);
    }

}
