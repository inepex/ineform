package com.inepex.ineForm.server.handler;

import javax.naming.NamingException;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.inepex.ineForm.server.EjbUtil;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListResult;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public class ObjectListHandler extends AbstractIneHandler<ObjectListAction, ObjectListResult> {

	private CustomActionHandler customActionHandler = null;
	
	public void setCustomActionHandler(CustomActionHandler customActionHandler) {
		this.customActionHandler = customActionHandler;
	}
	
	@Override
	public ObjectListResult doExecute(ObjectListAction action, ExecutionContext context)
							throws AuthenticationException, NamingException, DispatchException {
		String descriptorName = action.getDescriptorName();
		try {
			ObjectListResult result;
			if (customActionHandler !=null) {
				result = customActionHandler.doCustomAction(action);
				if (result != null)
					return result;
			}
			
			KVManipulatorDaoBase manipulatorDao = EjbUtil.get().getDefaultDaoForDescriptor(descriptorName);
			if (manipulatorDao == null)
				throw new ActionException("DAO not found for descriptor" + descriptorName);
			
			result = manipulatorDao.search(action);
			return result;

		} catch (ActionException e) {
			throw e;
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
