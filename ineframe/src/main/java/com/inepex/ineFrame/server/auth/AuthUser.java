package com.inepex.ineFrame.server.auth;

import java.util.Set;

public interface AuthUser {
	
	Long getUserId();
	String getFirstName();
	String getLastName();
	
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
