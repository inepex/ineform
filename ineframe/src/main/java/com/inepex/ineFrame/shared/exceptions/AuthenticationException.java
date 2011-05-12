package com.inepex.ineFrame.shared.exceptions;

import net.customware.gwt.dispatch.shared.ActionException;


public class AuthenticationException extends ActionException {

	public AuthenticationException(){
	}
	
	public AuthenticationException(String noRoleMSg) {
		super(noRoleMSg);
	}

	private static final long serialVersionUID = 1L;

}
