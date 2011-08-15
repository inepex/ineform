package com.inepex.ineom.shared.dispatch.interfaces;

public interface GenericResult {

    public String getMessage();

    public Boolean isSuccess();

    public void setMessage(String message);

    public void setSuccess(Boolean success);
    
}
