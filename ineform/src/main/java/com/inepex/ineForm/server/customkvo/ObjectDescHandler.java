package com.inepex.ineForm.server.customkvo;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.ObjectDescAction;
import com.inepex.ineFrame.shared.ObjectDescResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

@Singleton
public class ObjectDescHandler extends AbstractIneHandler<ObjectDescAction, ObjectDescResult>{

	private final CustomKVODao customKVODao;
	
	@Inject
	ObjectDescHandler(CustomKVODao customKVODao) {
		this.customKVODao=customKVODao;
	}

	@Override
	protected ObjectDescResult doExecute(ObjectDescAction action,
			ExecutionContext context) throws AuthenticationException,
			DispatchException {
		if(action.getId()==null)
			return null;
		
		CustomKVO cKvo = customKVODao.findById(action.getId());
		if(cKvo==null)
			return null;
		
		return new ObjectDescResult(CustomKVOMapperHelper.getODFromCustomKVO(cKvo));
	}
	
	@Override
	public Class<ObjectDescAction> getActionType() {
		return ObjectDescAction.class;
	}

}
