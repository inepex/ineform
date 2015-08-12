package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;

public class ResetEvent extends FormLifecycleEventBase<ResetEvent.Handler> {

    private static Type<ResetEvent.Handler> TYPE;

    public static interface Handler extends EventHandler {
        void onReset(ResetEvent event);
    }

    public static Type<ResetEvent.Handler> getType() {
        if (TYPE == null) {
            TYPE = new Type<ResetEvent.Handler>();
        }
        return TYPE;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onReset(this);
    }

}
