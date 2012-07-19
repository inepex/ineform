package com.inepex.ineForm.server.customkvo;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.CustomObjectDescAction;
import com.inepex.ineFrame.shared.CustomObjectDescResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

@Singleton
public class CustomObjectDescHandler extends AbstractIneHandler<CustomObjectDescAction, CustomObjectDescResult>{

	private final CustomKVODao customKVODao;
	
	@Inject
	CustomObjectDescHandler(CustomKVODao customKVODao) {
		this.customKVODao=customKVODao;
	}

	@Override
	protected CustomObjectDescResult doExecute(CustomObjectDescAction action,
			ExecutionContext context) throws AuthenticationException,
			DispatchException {
		if(action.getId()==null)
			return null;
		
		CustomKVO cKvo = customKVODao.findById(action.getId());
		if(cKvo==null)
			return null;
		
		return new CustomObjectDescResult(
				CustomKVOMapperHelper.getODFromCustomKVO(cKvo),
				action.isKvoNeed()
					? CustomKVOMapperHelper.getKVOFromCustomKVO(cKvo)
					: null);
	}
	
	@Override
	public Class<CustomObjectDescAction> getActionType() {
		return CustomObjectDescAction.class;
	}

}
