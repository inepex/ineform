package com.inepex.ineFrame.client.async;

public interface ConnectionFailedHandler {
	/**
	 * 
	 * @return true if caller should return
	 */
	public boolean startRecover();

	public boolean isOnline();
	
	public void shutdown();
}
