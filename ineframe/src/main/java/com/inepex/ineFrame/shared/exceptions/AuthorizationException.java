package com.inepex.ineFrame.shared.exceptions;

import net.customware.gwt.dispatch.shared.ActionException;

public class AuthorizationException extends ActionException {

    private static final long serialVersionUID = 1L;

    public AuthorizationException() {}

    public AuthorizationException(String message) {
        super(message);
    }

}
