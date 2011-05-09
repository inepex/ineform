package com.inepex.ineFrame.client.async;

import com.inepex.inei18n.client.IneFormI18n_old;

public interface AsyncStatusIndicator {
	public static String DEFAULT_LOADING  = IneFormI18n_old.loading();
	
	void onAsyncRequestStarted(String loadingMessage);
	void onGeneralFailure(String errorMessage);
	void onForbidden(String forbiddenMessage);
	void onUnAuthenticated();
	void onSuccess(String successMessage);
}
