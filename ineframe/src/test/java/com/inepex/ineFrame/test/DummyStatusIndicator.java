package com.inepex.ineFrame.test;

import com.inepex.ineFrame.client.async.AsyncStatusIndicator;

public class DummyStatusIndicator implements AsyncStatusIndicator {

	@Override
	public void onAsyncRequestStarted(String loadingMessage) {
	}

	@Override
	public void onGeneralFailure(String errorMessage) {
	}

	@Override
	public void onForbidden(String forbiddenMessage) {
	}

	@Override
	public void onUnAuthenticated() {
	}

	@Override
	public void onSuccess(String successMessage) {
	}

}
