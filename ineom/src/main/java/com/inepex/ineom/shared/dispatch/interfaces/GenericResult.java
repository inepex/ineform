package com.inepex.ineom.shared.dispatch.interfaces;

public interface GenericResult {

    String getMessage();

    Boolean isSuccess();

    void setMessage(String message);

    void setSuccess(Boolean success);

}
