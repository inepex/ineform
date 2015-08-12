package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;

public class RenderedEvent extends FormLifecycleEventBase<RenderedEvent.Handler> {

    private static Type<RenderedEvent.Handler> TYPE;

    public static interface Handler extends EventHandler {
        void onRendered(RenderedEvent event);
    }

    public static Type<RenderedEvent.Handler> getType() {
        if (TYPE == null) {
            TYPE = new Type<RenderedEvent.Handler>();
        }
        return TYPE;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onRendered(this);
    }

}
