package com.inepex.ineom.shared.dispatch.interfaces;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.validation.ValidationResult;

public interface ObjectManipulationResult extends GenericResult {

	public AssistedObject getObjectsNewState();

	public void setObjectsNewState(AssistedObject objectsNewState);

	public Long getNewObjectsId();

	public ValidationResult getValidationResult();

	public void setValidationResult(ValidationResult validationResult);
}
