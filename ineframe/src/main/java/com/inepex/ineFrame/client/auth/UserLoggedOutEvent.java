package com.inepex.ineFrame.client.auth;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserLoggedOutEvent extends GwtEvent<UserLoggedOutEvent.Handler>{
	
	public interface Handler extends EventHandler{
		public void onUserLoggedOut();
	}

	public static final Type<Handler> TYPE = new Type<Handler>();
	
	@Override
	public Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onUserLoggedOut();
	}
}
