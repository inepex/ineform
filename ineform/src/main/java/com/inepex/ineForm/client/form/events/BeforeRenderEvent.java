package com.inepex.ineForm.client.form.events;

import com.google.gwt.event.shared.EventHandler;

public class BeforeRenderEvent extends FormLifecycleEventBase<BeforeRenderEvent.Handler> {

    private static Type<BeforeRenderEvent.Handler> TYPE;

    public static interface Handler extends EventHandler {
        void onBeforeRender(BeforeRenderEvent event);
    }

    public static Type<BeforeRenderEvent.Handler> getType() {
        if (TYPE == null) {
            TYPE = new Type<BeforeRenderEvent.Handler>();
        }
        return TYPE;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onBeforeRender(this);
    }

}
