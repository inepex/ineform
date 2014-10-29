package com.inepex.ineFrame.client.navigation.messagepanel;

public interface MessagePanel {

	public void hideMessage();
	/**
	 * Show message on the message panel
	 * @param message Text to show
	 * @param isError True if it is an error message
	 * @param delayMillis The time in millis that after the message will disappear, 0 if it is not timed
	 */
	public void showMessage(String message, boolean isError, int delayInMillis);
	
	public void showMessage(String message, boolean isError);
	
	public int defaultDelay();

}
