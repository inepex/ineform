package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;

@Singleton
public class ObjectManipulationHandler implements ActionHandler<ObjectManipulationAction, ObjectManipulationActionResult> {
	
	private static final Logger _logger = LoggerFactory
			.getLogger(ObjectManipulationHandler.class);
	
	private final DaoFinder daoFinder;
	private CustomActionHandler customActionHandler = null;
	
	@Inject
	ObjectManipulationHandler(DaoFinder daoFinder) {
		this.daoFinder=daoFinder;
	}
	
	public void setCustomActionHandler(CustomActionHandler customActionHandler) {
		this.customActionHandler = customActionHandler;
	}

	@Override
	public ObjectManipulationActionResult execute(ObjectManipulationAction action, ExecutionContext context)
			throws DispatchException {
		String descriptorName = action.getObject().getDescriptorName();

//		System.out.printf("Manipulating object type '{}', id '{}'", descriptorName, action.getObject().getId());
		
		try {
			ObjectManipulationActionResult result;
			if (customActionHandler !=null) {
				result = customActionHandler.doCustomAction(action);
				if (result != null)
					return result;
			}
			
			// Do default behavior
			return (ObjectManipulationActionResult) daoFinder.getDefaultDaoForDescriptor(descriptorName).manipulate(action);

		} catch (Exception e) {
			_logger.warn(e.getMessage(), e);
			throw new ActionException("Problem while performing manipulate action: " + e.getMessage());
		}
	}
	
	@Override
	public Class<ObjectManipulationAction> getActionType() {
		return ObjectManipulationAction.class;
	}

	@Override
	public void rollback(ObjectManipulationAction action, ObjectManipulationActionResult result, ExecutionContext context)
			throws DispatchException {

	}
	

}
