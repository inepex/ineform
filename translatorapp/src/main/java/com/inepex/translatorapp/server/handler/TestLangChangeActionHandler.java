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
		res.setWillBeDeletedWithEmpty(nums[0]==null ? 0 : ((Number)nums[0]).longValue());
		res.setWillBeDeletedWithText(nums[1]==null ? 0 : ((Number)nums[1]).longValue());
		return res;
	}

	@Override
	public Class<TestLangChangeAction> getActionType() {
		return TestLangChangeAction.class;
	}
}
