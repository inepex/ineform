package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineFrame.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * The result for {@link ObjectManipulationAction}
 *
 * @author István Szoboszlai
 *
 */
public class ObjectManipulationResult extends GenericResult {

	private static final long serialVersionUID = 5884303239610810826L;

	private long newObjectsId = -1;
	private AssistedObject objectsNewState = null;

	private ValidationResult validationResult;

	public ObjectManipulationResult() {
	}

	public ObjectManipulationResult(AssistedObject objectsNewState) {
		super();
		this.objectsNewState = objectsNewState;
	}

	public ObjectManipulationResult(long newObjectsId) {
		super();
		this.newObjectsId = newObjectsId;
	}

	public ObjectManipulationResult(AssistedObject objectsNewState, String message, boolean success) {
		super(message, success);
		this.objectsNewState = objectsNewState;
	}

	public ObjectManipulationResult(ValidationResult validationResult) {
		super("", false);
		this.validationResult = validationResult;
	}

	public AssistedObject getObjectsNewState() {
		return objectsNewState;
	}

	public void setObjectsNewState(AssistedObject objectsNewState) {
		this.objectsNewState = objectsNewState;
	}

	public long getNewObjectsId() {
		return newObjectsId;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(ValidationResult validationResult) {
		this.validationResult = validationResult;
	}

}
