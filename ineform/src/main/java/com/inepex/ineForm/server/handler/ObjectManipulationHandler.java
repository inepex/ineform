package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.ineForm.server.EjbUtil;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;

public class ObjectManipulationHandler implements ActionHandler<ObjectManipulationAction, ObjectManipulationResult> {

	final Logger logger = LoggerFactory.getLogger(ObjectManipulationHandler.class);
	
	private CustomActionHandler customActionHandler = null;
	
	public void setCustomActionHandler(CustomActionHandler customActionHandler) {
		this.customActionHandler = customActionHandler;
	}

	@Override
	public ObjectManipulationResult execute(ObjectManipulationAction action, ExecutionContext context)
			throws DispatchException {
		String descriptorName = action.getObject().getDescriptorName();

		logger.debug("Manipulating object type '{}', id '{}'", descriptorName, action.getObject().getId());
		
		try {
			ObjectManipulationResult result;
			if (customActionHandler !=null) {
				result = customActionHandler.doCustomAction(action);
				if (result != null)
					return result;
			}
			
			// Do default behavior
			KVManipulatorDaoBase manipulatorDao = EjbUtil.get().getDefaultDaoForDescriptor(descriptorName);
			if (manipulatorDao == null)
				throw new ActionException("DAO not found for descriptor " + descriptorName);

			result = manipulatorDao.manipulate(action);
			return result;
		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Problem while performing manipulate action: " + e.getMessage());
		}
	}
	
	@Override
	public Class<ObjectManipulationAction> getActionType() {
		return ObjectManipulationAction.class;
	}

	@Override
	public void rollback(ObjectManipulationAction action, ObjectManipulationResult result, ExecutionContext context)
			throws DispatchException {

	}
	

}
