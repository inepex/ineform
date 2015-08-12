package com.inepex.ineForm.shared.dispatch;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * The result for {@link ObjectManipulationAction}
 *
 * @author István Szoboszlai
 *
 */
public class ObjectManipulationActionResult extends GenericActionResult
    implements
    ObjectManipulationResult {

    private static final long serialVersionUID = 5884303239610810826L;

    private Long newObjectsId = -1L;
    private AssistedObject objectsNewState = null;

    private ValidationResult validationResult;

    public ObjectManipulationActionResult() {}

    public ObjectManipulationActionResult(AssistedObject objectsNewState) {
        super();
        this.objectsNewState = objectsNewState;
    }

    public ObjectManipulationActionResult(long newObjectsId) {
        super();
        this.newObjectsId = newObjectsId;
    }

    public ObjectManipulationActionResult(
        AssistedObject objectsNewState,
        String message,
        boolean success) {
        super(message, success);
        this.objectsNewState = objectsNewState;
    }

    public ObjectManipulationActionResult(ValidationResult validationResult) {
        super("", false);
        this.validationResult = validationResult;
    }

    @Override
    public AssistedObject getObjectsNewState() {
        return objectsNewState;
    }

    @Override
    public void setObjectsNewState(AssistedObject objectsNewState) {
        this.objectsNewState = objectsNewState;
    }

    @Override
    public Long getNewObjectsId() {
        return newObjectsId;
    }

    @Override
    public ValidationResult getValidationResult() {
        return validationResult;
    }

    @Override
    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

}
