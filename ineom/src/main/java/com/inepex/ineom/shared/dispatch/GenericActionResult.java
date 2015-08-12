package com.inepex.ineom.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Horváth Szabolcs
 *
 */
public class GenericActionResult implements Result {

    private String message = null;
    private Boolean success = true;

    public GenericActionResult() {}

    public GenericActionResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setError(String message) {
        setMessage(message);
        setSuccess(false);
    }
}
