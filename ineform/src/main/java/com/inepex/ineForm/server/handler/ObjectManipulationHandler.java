package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;

@Singleton
public class ObjectManipulationHandler implements ActionHandler<ObjectManipulationAction, ObjectManipulationResult> {
	
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
	public ObjectManipulationResult execute(ObjectManipulationAction action, ExecutionContext context)
			throws DispatchException {
		String descriptorName = action.getObject().getDescriptorName();

		System.out.printf("Manipulating object type '{}', id '{}'", descriptorName, action.getObject().getId());
		
		try {
			ObjectManipulationResult result;
			if (customActionHandler !=null) {
				result = customActionHandler.doCustomAction(action);
				if (result != null)
					return result;
			}
			
			// Do default behavior
			return daoFinder.getDefaultDaoForDescriptor(descriptorName).manipulate(action);

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
