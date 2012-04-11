package com.inepex.ineFrame.client.async;


public interface AsyncStatusIndicator {
	void onAsyncRequestStarted(String loadingMessage);
	void onGeneralFailure(String errorMessage);
	void onForbidden(String forbiddenMessage);
	void onUnAuthenticated();
	/**
	 * @param successMessage set it to null if you doesn't want to show message
	 */
	void onSuccess(String successMessage);
}
