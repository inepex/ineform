package com.inepex.ineFrame.client.auth;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserLoggedInEvent extends GwtEvent<UserLoggedInEvent.Handler> {

    public interface Handler extends EventHandler {
        public void onUserLoggedIn();
    }

    public static final Type<Handler> TYPE = new Type<Handler>();

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onUserLoggedIn();
    }
}
