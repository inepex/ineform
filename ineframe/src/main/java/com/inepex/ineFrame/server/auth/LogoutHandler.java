package com.inepex.ineFrame.server.auth;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.LogoutAction;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.dispatch.GenericActionResult;

@Singleton
public class LogoutHandler extends AbstractIneHandler<LogoutAction, GenericActionResult> {

    private final Provider<HttpSession> sessionProvider;

    @Inject
    LogoutHandler(Provider<HttpSession> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public Class<LogoutAction> getActionType() {
        return LogoutAction.class;
    }

    @Override
    protected GenericActionResult doExecute(LogoutAction action, ExecutionContext context)
        throws AuthenticationException,
        DispatchException {

        sessionProvider.get().invalidate();

        return new GenericActionResult("", true);
    }

}
