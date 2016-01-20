package com.inepex.ineFrame.server.dispatch;

import javax.naming.NamingException;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;
import com.inepex.ineom.shared.validation.ValidationException;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * Abstract class for Object Manipulation type of actions. Makes it easier to
 * prepare the result that a {@link IneForm} can consume. Sets
 * {@link ValidationResult} when {@link ValidationException} is thrown. Sends
 * default error message when {@link NamingException} is encountered
 * 
 * @author istvanszoboszlai
 *
 * @param <A>
 */
public abstract class AbstractIneHandler<A extends Action<R>, R extends GenericActionResult>
        implements ActionHandler<A, R> {

    @Override
    public R execute(A arg0, ExecutionContext arg1) throws DispatchException {

        return doExecute(arg0, arg1);
    }

    protected void doValidate(KeyValueObjectValidationManager validatorManager, KeyValueObject kvo)
        throws ValidationException {
        ValidationResult vr = validatorManager.validate(kvo);
        if (!vr.isValid())
            throw new ValidationException(vr);
    }

    /**
     * @param action
     * @param context
     * @throws ValidationException
     * @throws AuthenticationException
     * @throws NamingException
     */
    protected abstract R doExecute(A action, ExecutionContext context)
        throws AuthenticationException,
        DispatchException;

    @Override
    public void rollback(A arg0, R arg1, ExecutionContext arg2) throws DispatchException {}

}
