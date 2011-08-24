package com.inepex.ineForm.server.handler;

import javax.naming.NamingException;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;
import com.inepex.ineom.shared.validation.ValidationException;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * Abstract class for Object Manipulation type of actions.
 * Makes it easier to prepare the result that a {@link IneForm} can consume.
 * Sets {@link ValidationResult} when {@link ValidationException} is thrown.
 * 
 * @author istvanszoboszlai
 *
 * @param <A>
 */
public abstract class AbstractObjectManipulationHandler<A extends Action<ObjectManipulationActionResult>> implements
		ActionHandler<A, ObjectManipulationActionResult> {
	
	final KeyValueObjectValidationManager validatorManager;
	
	public AbstractObjectManipulationHandler(KeyValueObjectValidationManager validatorManager) {
		this.validatorManager = validatorManager;
	}
	
	@Override
	public ObjectManipulationActionResult execute(A arg0, ExecutionContext arg1)
			throws DispatchException {
		ObjectManipulationActionResult result = null;
		try {
			result = doExecute(arg0, arg1);
		} catch (ValidationException e) {
			result = new ObjectManipulationActionResult(e.getValidationResult());
		}
		
		return result;
	}
	
	protected void doValidate(KeyValueObject kvo) throws ValidationException {
		ValidationResult vr = validatorManager.validate(kvo);
		if(!vr.isValid()) 
			throw new ValidationException(vr);	
	}
	
	/**
	 * @param action
	 * @param context
	 * @throws ValidationException
	 * @throws AuthenticationException
	 * @throws NamingException
	 */
	protected abstract ObjectManipulationActionResult doExecute(A action, ExecutionContext context)
							throws ValidationException, AuthenticationException, ActionException;


	@Override
	public void rollback(A arg0, ObjectManipulationActionResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}

}
