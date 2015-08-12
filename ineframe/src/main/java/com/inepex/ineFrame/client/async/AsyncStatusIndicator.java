package com.inepex.ineFrame.client.async;

public interface AsyncStatusIndicator {
    void onAsyncRequestStarted(String loadingMessage);

    void onGeneralFailure(String errorMessage);

    /**
     * @param successMessage
     *            set it to null if you doesn't want to show message
     */
    void onSuccess(String successMessage);
}
