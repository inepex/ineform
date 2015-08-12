package com.inepex.ineFrame.shared.exceptions;

import net.customware.gwt.dispatch.shared.ActionException;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthenticationException extends ActionException implements IsSerializable {

    public AuthenticationException() {}

    public AuthenticationException(String noRoleMSg) {
        super(noRoleMSg);
    }

    private static final long serialVersionUID = 1L;

}
