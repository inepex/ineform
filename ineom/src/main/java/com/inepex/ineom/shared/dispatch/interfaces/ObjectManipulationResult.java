package com.inepex.ineom.shared.dispatch.interfaces;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.validation.ValidationResult;

public interface ObjectManipulationResult extends GenericResult {

    AssistedObject getObjectsNewState();

    void setObjectsNewState(AssistedObject objectsNewState);

    Long getNewObjectsId();

    ValidationResult getValidationResult();

    void setValidationResult(ValidationResult validationResult);
}
