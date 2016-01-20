package com.inepex.ineFrame.server.di.guice;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.BatchActionHandler;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import net.customware.gwt.dispatch.shared.BatchAction;

import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.inepex.ineFrame.server.auth.AbstractLoginHandler;
import com.inepex.ineFrame.server.auth.AuthUser;
import com.inepex.ineFrame.server.auth.CaptchaInfoHandler;
import com.inepex.ineFrame.server.auth.DefaultLoginHandler;
import com.inepex.ineFrame.server.auth.GetAuthStatusHandler;
import com.inepex.ineFrame.server.auth.LogoutHandler;
import com.inepex.ineFrame.server.handler.GetDescriptorStoreHandler;
import com.inepex.ineFrame.server.handler.GetTimeZoneNamesHandler;
import com.inepex.ineFrame.server.handler.PingActionHandler;
import com.inepex.ineFrame.shared.GetDescStore;
import com.inepex.ineFrame.shared.GetTimeZoneNamesAction;
import com.inepex.ineFrame.shared.PingAction;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.CaptchaInfoAction;
import com.inepex.ineFrame.shared.auth.GetAuthStatusAction;
import com.inepex.ineFrame.shared.auth.LoginAction;
import com.inepex.ineFrame.shared.auth.LogoutAction;
import com.inepex.inei18n.server.ChangeLanguageHandler;
import com.inepex.inei18n.server.GetI18nModulesAndSetCurrentLangFromCookieHandler;
import com.inepex.inei18n.shared.ChangeLanguageAction;
import com.inepex.inei18n.shared.GetI18nModulesAndSetCurrentLangFromCookieAction;

public class IneFrameBaseActionHandlerModule extends ActionHandlerModule {

    private Class<? extends AbstractLoginHandler<AuthUser, AuthStatusResultBase>> loginHandler = DefaultLoginHandler.class;
    private Class<? extends ActionHandler<GetAuthStatusAction, AuthStatusResultBase>> getAuthStatusHandler = GetAuthStatusHandler.class;

    public IneFrameBaseActionHandlerModule() {
        super();
    }

    public IneFrameBaseActionHandlerModule setLoginHandler(
        Class<? extends AbstractLoginHandler<AuthUser, AuthStatusResultBase>> loginHandler) {
        this.loginHandler = loginHandler;
        return this;
    }

    public IneFrameBaseActionHandlerModule setGetAuthStatusHandler(
        Class<? extends ActionHandler<GetAuthStatusAction, AuthStatusResultBase>> getAuthStatusHandler) {
        this.getAuthStatusHandler = getAuthStatusHandler;
        return this;
    }

    @Override
    protected void configureHandlers() {
        bindHandler(BatchAction.class, BatchActionHandler.class);
        bindHandler(ChangeLanguageAction.class, ChangeLanguageHandler.class);
        bindHandler(
            GetI18nModulesAndSetCurrentLangFromCookieAction.class,
            GetI18nModulesAndSetCurrentLangFromCookieHandler.class);
        bindHandler(GetTimeZoneNamesAction.class, GetTimeZoneNamesHandler.class);
        bindHandler(GetDescStore.class, GetDescriptorStoreHandler.class);

        // authentication
        bindHandler(GetAuthStatusAction.class, getAuthStatusHandler);
        bindHandler(LogoutAction.class, LogoutHandler.class);
        bindHandler(CaptchaInfoAction.class, CaptchaInfoHandler.class);
        bind(new TypeLiteral<AbstractLoginHandler<AuthUser, AuthStatusResultBase>>() {})
            .to(loginHandler)
            .in(Singleton.class);
        bindHandler(LoginAction.class, loginHandler);
        bindHandler(PingAction.class, PingActionHandler.class);

    }
}
