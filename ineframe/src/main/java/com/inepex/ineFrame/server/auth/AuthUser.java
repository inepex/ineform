package com.inepex.ineFrame.server.auth;

import java.util.Set;

public interface AuthUser {
	
	Long getUserId();
	String getDisplayName();
	
	/**
	 * for login (may e-mail address, may nickname...)
	 * 
	 */
	String getUserAuthString();
	
	/**
	 * role set of user
	 */
	Set<String> getAllowedRoles();

}
