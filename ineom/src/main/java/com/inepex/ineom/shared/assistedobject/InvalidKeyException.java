package com.inepex.ineom.shared.assistedobject;

@SuppressWarnings("serial")
public class InvalidKeyException extends RuntimeException {

    public InvalidKeyException() {}

    public InvalidKeyException(String message) {
        super(message);
    }
}
