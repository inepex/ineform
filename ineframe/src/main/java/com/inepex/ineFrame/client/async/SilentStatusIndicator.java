package com.inepex.ineFrame.client.async;

/**
 * This is an Implementation of {@link AsyncStatusIndicator} that does not show
 * or do anything. Only logs to console Be careful when using it.
 * 
 * @author istvanszoboszlai
 *
 */
public class SilentStatusIndicator implements AsyncStatusIndicator {

    @Override
    public void onAsyncRequestStarted(String loadingMessage) {}

    @Override
    public void onGeneralFailure(String errorMessage) {
        System.out.println("SilentStatusIndicator: onGeneralFailure!");
    }

    @Override
    public void onSuccess(String successMessage) {}

}
