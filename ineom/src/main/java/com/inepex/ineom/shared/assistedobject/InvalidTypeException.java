package com.inepex.ineom.shared.assistedobject;

@SuppressWarnings("serial")
public class InvalidTypeException extends RuntimeException {

	public InvalidTypeException() {
	}

	public InvalidTypeException(String message) {
		super(message);
	}
}
