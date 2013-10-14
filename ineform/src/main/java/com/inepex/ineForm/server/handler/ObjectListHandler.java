package com.inepex.ineForm.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.DaoFinder;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

@Singleton
public class ObjectListHandler extends AbstractIneHandler<ObjectListAction, ObjectListActionResult> {
	
	private static final Logger _logger = LoggerFactory
			.getLogger(ObjectListHandler.class);


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
	public ObjectListActionResult doExecute(ObjectListAction action, ExecutionContext context)
							throws AuthenticationException, DispatchException {
		String descriptorName = action.getDescriptorName();
		try {
			ObjectListActionResult result;
			if (customActionHandler !=null) {
				result = customActionHandler.doCustomAction(action);
				if (result != null)
					return result;
			}
			
			return (ObjectListActionResult)daoFinder.getDefaultDaoForDescriptor(descriptorName).search(action);

		} catch (Exception e) {
			e.printStackTrace();
			_logger.warn(e.getMessage());
			throw new ActionException("Problem while performing search action: " + e.getMessage());
		}
	}

	@Override
	public Class<ObjectListAction> getActionType() {
		return ObjectListAction.class;
	}
}
