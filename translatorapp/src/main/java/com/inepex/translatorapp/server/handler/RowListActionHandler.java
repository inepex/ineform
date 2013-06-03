package com.inepex.translatorapp.server.handler;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.dao.ModuleRowDao;
import com.inepex.translatorapp.server.entity.mapper.ModuleRowMapper;
import com.inepex.translatorapp.shared.action.RowListAction;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;

public class RowListActionHandler extends AbstractIneHandler<RowListAction, ObjectListActionResult> {

	@Inject ModuleRowDao dao;
	@Inject ModuleRowMapper mapper;
	
	@Override
	protected ObjectListActionResult doExecute(RowListAction action, ExecutionContext context) throws AuthenticationException, DispatchException {

		List<ModuleRow> values = dao.listForPage(action.getFirstResult(), action.getNumMaxResult(), action.getMagicString(), action.getModuleId());
		
		ObjectListActionResult res = new ObjectListActionResult();
		res.setDescriptorName(TranslateTableRowConsts.descriptorName);
		if (action.isQueryResultCount()) {
			res.setAllResultCount((long) values.size());
		}
		
		if (action.getNumMaxResult() > 0)
			res.setList(mapper.entityListToKvoList(values));

		
		return res;
	}

	@Override
	public Class<RowListAction> getActionType() {
		return RowListAction.class;
	}
}
