package com.inepex.ineom.shared.kvo;

public class InvalidKeyException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1146288007549495343L;

	public InvalidKeyException() {
	}

	public InvalidKeyException(String message) {
		super(message);
	}
}
