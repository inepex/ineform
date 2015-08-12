package com.inepex.translatorapp.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.google.inject.matcher.Matchers;
import com.inepex.ineForm.server.guice.IneFormActionHanlderModule;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseServletModule;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;
import com.inepex.translatorapp.server.entity.dao.ModuleRowDao;
import com.inepex.translatorapp.server.entity.dao.TranslatedValueDao;
import com.inepex.translatorapp.server.handler.LangChangeActionHandler;
import com.inepex.translatorapp.server.handler.LoginHandler;
import com.inepex.translatorapp.server.handler.RegActionHandler;
import com.inepex.translatorapp.server.handler.RowListActionHandler;
import com.inepex.translatorapp.server.handler.RowUploadActionHandler;
import com.inepex.translatorapp.server.handler.TestLangChangeActionHandler;
import com.inepex.translatorapp.server.handler.TransTableListActionHandler;
import com.inepex.translatorapp.shared.action.LangChangeAction;
import com.inepex.translatorapp.shared.action.RegAction;
import com.inepex.translatorapp.shared.action.RowListAction;
import com.inepex.translatorapp.shared.action.RowUploadAction;
import com.inepex.translatorapp.shared.action.TestLangChangeAction;
import com.inepex.translatorapp.shared.action.TransTableListAction;

public class AppGuiceModule extends ActionHandlerModule {

    @Override
    protected void configureHandlers() {
        install(new IneFrameBaseServletModule("translatorapp", AppDispatchServlet.class));
        install(new IneFrameBaseModule(true).setLoginHandler(LoginHandler.class).setAppLangs(
            TranslatorAppLangs.class));
        install(new IneFormActionHanlderModule());

        bindHandler(RegAction.class, RegActionHandler.class);
        bindHandler(TransTableListAction.class, TransTableListActionHandler.class);
        bindHandler(RowListAction.class, RowListActionHandler.class);
        bindHandler(TestLangChangeAction.class, TestLangChangeActionHandler.class);
        bindHandler(LangChangeAction.class, LangChangeActionHandler.class);
        bindHandler(RowUploadAction.class, RowUploadActionHandler.class);

        bindInterceptor(Matchers.subclassesOf(TranslatedValueDao.class), Matchers.returns(Matchers
            .subclassesOf(ObjectManipulationResult.class)), new TransValueModInterceptor(
            getProvider(SessionScopedAuthStat.class),
            getProvider(AssistedObjectHandlerFactory.class)));

        bindInterceptor(Matchers.subclassesOf(ModuleRowDao.class), Matchers.returns(Matchers
            .subclassesOf(ObjectManipulationResult.class)), new ModuleRowModInterceptor(
            getProvider(SessionScopedAuthStat.class),
            getProvider(AssistedObjectHandlerFactory.class)));
    }

}