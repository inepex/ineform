package com.inepex.ineFrame.client.async;

public abstract class SimpleFailureStatusIndicator implements AsyncStatusIndicator {

	@Override
	public void onAsyncRequestStarted(String loadingMessage) {
	}

	@Override
	public void onSuccess(String successMessage) {
	}

	@Override
	public void onGeneralFailure(String errorMessage) {
		onAnyFailure(errorMessage);
	}
	
	protected abstract void onAnyFailure(String message);

}
