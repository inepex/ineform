package com.inepex.ineFrame.shared.exceptions;

import net.customware.gwt.dispatch.shared.DispatchException;

public class PublicException extends DispatchException {

	private static final long serialVersionUID = 1L;

	public PublicException() {
	}
	
	public PublicException(String message) {
		super(message);
	}

}
