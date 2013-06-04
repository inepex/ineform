package com.inepex.translatorapp.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.translatorapp.server.entity.dao.TranslatedValueDao;
import com.inepex.translatorapp.shared.action.TestLangChangeAction;
import com.inepex.translatorapp.shared.action.TestLangChangeResult;

public class TestLangChangeActionHandler extends AbstractIneHandler<TestLangChangeAction, TestLangChangeResult>{

	@Inject TranslatedValueDao dao;
	
	@Override
	protected TestLangChangeResult doExecute(TestLangChangeAction action,
			ExecutionContext context) throws AuthenticationException,
			DispatchException {
		
		Object[] nums = dao.countForLangAndModule(action.getLangId(), action.getModuleId());
		
		TestLangChangeResult res = new TestLangChangeResult();
		res.setWillBeDeletedWithEmpty(((Number)nums[0]).longValue());
		res.setWillBeDeletedWithText(((Number)nums[1]).longValue());
		return res;
	}

	@Override
	public Class<TestLangChangeAction> getActionType() {
		return TestLangChangeAction.class;
	}
}
