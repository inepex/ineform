package com.inepex.ineFrame.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

import net.customware.gwt.dispatch.shared.ActionException;

public class AuthorizationException extends ActionException implements IsSerializable {

    private static final long serialVersionUID = 1L;

    public AuthorizationException() {}

    public AuthorizationException(String message) {
        super(message);
    }

}
