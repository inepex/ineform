package com.inepex.ineFrame.client.async;


public interface AsyncStatusIndicator {
	void onAsyncRequestStarted(String loadingMessage);
	void onGeneralFailure(String errorMessage);
	void onForbidden(String forbiddenMessage);
	void onUnAuthenticated();
	void onSuccess(String successMessage);
}
