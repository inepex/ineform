package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.inepex.ineForm.server.EjbUtil;
import com.inepex.ineForm.server.KVManipulatorDaoBase;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListResult;

public class RelationListHandler implements ActionHandler<RelationListAction, RelationListResult> {

	@Override
	public RelationListResult execute(RelationListAction action, ExecutionContext context) throws DispatchException {
		String descriptorName = action.getDescriptorName();
		try {
			RelationListResult result;
			KVManipulatorDaoBase manipulatorDao = EjbUtil.get().getDefaultDaoForDescriptor(descriptorName);
			if (manipulatorDao == null)
				throw new ActionException("DAO not found for descriptor" + descriptorName);
			
			result = manipulatorDao.searchAsRelation(action);
			return result;

		} catch (ActionException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("Problem while performing search action: " + e.getMessage());
		}
	}

	@Override
	public Class<RelationListAction> getActionType() {
		return RelationListAction.class;
	}

	@Override
	public void rollback(RelationListAction action, RelationListResult result, ExecutionContext context)
			throws DispatchException {

	}

}
