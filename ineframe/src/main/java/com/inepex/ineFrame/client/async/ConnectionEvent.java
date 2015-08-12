package com.inepex.ineFrame.client.async;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.inepex.ineFrame.client.async.ConnectionEvent.ConnectionEventHandler;

public class ConnectionEvent extends GwtEvent<ConnectionEventHandler> {

    public interface ConnectionEventHandler extends EventHandler {

        public void onEvent(ConnectionEvent e);
    }

    public static Type<ConnectionEventHandler> TYPE = new Type<ConnectionEventHandler>();

    private boolean failure;
    private int reconnectionDelay;

    public ConnectionEvent(boolean failure) {
        super();
        this.failure = failure;
    }

    public ConnectionEvent(boolean failure, int reconnectionDelay) {
        super();
        this.failure = failure;
        this.reconnectionDelay = reconnectionDelay;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public int getReconnectionDelay() {
        return reconnectionDelay;
    }

    public void setReconnectionDelay(int reconnectionDelay) {
        this.reconnectionDelay = reconnectionDelay;
    }

    @Override
    protected void dispatch(ConnectionEventHandler handler) {
        handler.onEvent(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ConnectionEventHandler> getAssociatedType() {
        return TYPE;
    }
}