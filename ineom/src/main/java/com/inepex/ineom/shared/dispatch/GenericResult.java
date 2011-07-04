package com.inepex.ineom.shared.dispatch;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Horváth Szabolcs
 *
 */
public class GenericResult implements Result {
    
	private static final long serialVersionUID = 1L;
	private String message = null;
    private boolean success = true;

    public GenericResult() {
    }

    public GenericResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setError(String message) {
        setMessage(message);
        setSuccess(false);
    }
}
