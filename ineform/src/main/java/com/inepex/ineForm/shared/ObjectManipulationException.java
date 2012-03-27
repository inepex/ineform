package com.inepex.ineForm.shared;

@SuppressWarnings("serial")
public class ObjectManipulationException extends Exception {

	public static enum Reason {
		/**
		 * Manipulation is denied by constraint violation check.
		 */
		ConstraintViolationFailed;
	}
	
	private Reason reason;
	
	ObjectManipulationException() {
	}
	
	public ObjectManipulationException(Reason reason) {
		this.reason=reason;
	}
	
	public Reason getReason() {
		return reason;
	}
	
}
