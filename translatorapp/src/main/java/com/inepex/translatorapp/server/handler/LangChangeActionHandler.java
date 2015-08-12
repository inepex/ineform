package com.inepex.translatorapp.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.translatorapp.server.entity.dao.ModuleDao;
import com.inepex.translatorapp.shared.action.LangChangeAction;

public class LangChangeActionHandler
    extends
    AbstractIneHandler<LangChangeAction, GenericActionResult> {

    @Inject
    ModuleDao moduleDao;
    @Inject
    Provider<SessionScopedAuthStat> authProv;

    @Override
    protected GenericActionResult doExecute(LangChangeAction action, ExecutionContext context)
        throws AuthenticationException,
        DispatchException {

        if (action.getCurrentState()) {
            moduleDao.removeLang(action.getLangId(), action.getModuleId());
        } else {
            SessionScopedAuthStat stat = authProv.get();
            Long userId;
            synchronized (stat) {
                userId = stat.getUserId();
            }

            moduleDao.addLang(action.getLangId(), action.getModuleId(), userId);
        }

        return new GenericActionResult();
    }

    @Override
    public Class<LangChangeAction> getActionType() {
        return LangChangeAction.class;
    }
}
