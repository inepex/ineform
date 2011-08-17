package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;

public class RelationListHandler implements ActionHandler<RelationListAction, RelationListActionResult> {

	private final DaoFinder daoFinder;
	
	@Inject
	RelationListHandler(DaoFinder daoFinder) {
		this.daoFinder=daoFinder;
	}
	
	@Override
	public RelationListActionResult execute(RelationListAction action, ExecutionContext context) throws DispatchException {
		String descriptorName = action.getDescriptorName();
		try {
			return (RelationListActionResult)daoFinder.getDefaultDaoForDescriptor(descriptorName).searchAsRelation(action);
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
	public void rollback(RelationListAction action, RelationListActionResult result, ExecutionContext context)
			throws DispatchException {

	}

}
