package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineom.shared.dispatch.GenericResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * The result for {@link ObjectManipulationAction}
 *
 * @author István Szoboszlai
 *
 */
public class ObjectManipulationActionResult extends GenericResult implements ObjectManipulationResult {

	private static final long serialVersionUID = 5884303239610810826L;

	private Long newObjectsId = -1L;
	private AssistedObject objectsNewState = null;

	private ValidationResult validationResult;

	public ObjectManipulationActionResult() {
	}

	public ObjectManipulationActionResult(AssistedObject objectsNewState) {
		super();
		this.objectsNewState = objectsNewState;
	}

	public ObjectManipulationActionResult(long newObjectsId) {
		super();
		this.newObjectsId = newObjectsId;
	}

	public ObjectManipulationActionResult(AssistedObject objectsNewState, String message, boolean success) {
		super(message, success);
		this.objectsNewState = objectsNewState;
	}

	public ObjectManipulationActionResult(ValidationResult validationResult) {
		super("", false);
		this.validationResult = validationResult;
	}

	public AssistedObject getObjectsNewState() {
		return objectsNewState;
	}

	public void setObjectsNewState(AssistedObject objectsNewState) {
		this.objectsNewState = objectsNewState;
	}

	public Long getNewObjectsId() {
		return newObjectsId;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(ValidationResult validationResult) {
		this.validationResult = validationResult;
	}

}