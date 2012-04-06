package com.inepex.ineFrame.client.async;

import com.google.gwt.user.client.Window;

public class DefaultFailedHandler implements ConnectionFailedHandler {

	@Override
	public boolean isOnline() {
		return true;
	}

	@Override
	public boolean startRecover() {
		Window.Location.reload();
		return true;
	}

	@Override
	public void shutdown() {
	}
}
