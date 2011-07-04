package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.dispatch.ObjectListAction;
import com.inepex.ineom.shared.dispatch.ObjectListResult;

@Singleton
public class ObjectListHandler extends AbstractIneHandler<ObjectListAction, ObjectListResult> {

	private final DaoFinder daoFinder;
	private CustomActionHandler customActionHandler = null;
	
	@Inject
	ObjectListHandler(DaoFinder daoFinder) {
		this.daoFinder=daoFinder;
	}
	
	public void setCustomActionHandler(CustomActionHandler customActionHandler) {
		this.customActionHandler = customActionHandler;
	}
	
	@Override
	public ObjectListResult doExecute(ObjectListAction action, ExecutionContext context)
							throws AuthenticationException, DispatchException {
		String descriptorName = action.getDescriptorName();
		try {
			ObjectListResult result;
			if (customActionHandler !=null) {
				result = customActionHandler.doCustomAction(action);
				if (result != null)
					return result;
			}
			
			return daoFinder.getDefaultDaoForDescriptor(descriptorName).search(action);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Problem while performing search action: " + e.getMessage());
		}
	}

	@Override
	public Class<ObjectListAction> getActionType() {
		return ObjectListAction.class;
	}
}
