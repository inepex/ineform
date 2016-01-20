package com.inepex.ineFrame.server;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.async.DefaultFailedHandler;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.SilentStatusIndicator;
import com.inepex.ineFrame.server.di.guice.DefaultApplicationLangs;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseActionHandlerModule;
import com.inepex.ineFrame.server.di.guice.IneFrameBaseServletModule;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.dispatch.Loggable;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.inei18n.server.ApplicationLangs;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.ConcurrentDescStoreMapCreator;
import com.inepex.ineom.server.DescStoreCreator;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptorstore.ClientDescStoreCreator;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStoreMapCreator;
import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class DispatchMock {

    protected Injector injector = Guice.createInjector(
        new IneFrameBaseActionHandlerModule(),
        new IneFrameBaseServletModule("test", TestDispatchServlet.class),
        new AbstractModule() {
            @Override
            protected void configure() {
                bind(DescriptorStore.class).to(MultiLangDescStore.class);
                bind(DescriptorStoreMapCreator.class).to(ConcurrentDescStoreMapCreator.class);
                bind(DescStoreCreator.class).to(ClientDescStoreCreator.class);
                bind(CurrentLang.class).to(MockCurrentLang.class);
                bind(ApplicationLangs.class).to(DefaultApplicationLangs.class).in(Singleton.class);
            }
        },
        new ActionHandlerModule() {
            @Override
            protected void configureHandlers() {
                bindHandler(Action1.class, Action1Handler.class);
                bindHandler(Action2.class, Action2Handler.class);
                bindHandler(ActionThrowsException.class, ActionThrowsExceptionHandler.class);
            }
        });

    private TestDispatchServlet dispatchServlet = injector.getInstance(TestDispatchServlet.class);

    public static class Result1 extends GenericActionResult {}

    public static class Action1 implements Action<Result1> {};

    public static class Action1Handler extends AbstractIneHandler<Action1, Result1> {
        @Override
        public Class<Action1> getActionType() {
            return Action1.class;
        }

        @Override
        protected Result1 doExecute(Action1 action, ExecutionContext context)
            throws AuthenticationException,
            DispatchException {
            return new Result1();
        }
    }

    public static class Result2 extends GenericActionResult {}

    public static class Action2 implements Action<Result2> {};

    public static class Action2Handler extends AbstractIneHandler<Action2, Result2> {
        @Override
        public Class<Action2> getActionType() {
            return Action2.class;
        }

        @Override
        protected Result2 doExecute(Action2 action, ExecutionContext context)
            throws AuthenticationException,
            DispatchException {
            return new Result2();
        }
    }

    public static class Result3 extends GenericActionResult {}

    public static class ActionThrowsException implements Action<Result3> {};

    public static class ActionThrowsExceptionHandler
            extends AbstractIneHandler<ActionThrowsException, Result3> {
        @Override
        public Class<ActionThrowsException> getActionType() {
            return ActionThrowsException.class;
        }

        @Override
        protected Result3 doExecute(ActionThrowsException action, ExecutionContext context)
            throws AuthenticationException,
            DispatchException {
            throw new ActionException("Planned test case exception");
        }
    }

    public static class TestDispatchServlet extends AbstractGuiceDispatch {

        @Inject
        public TestDispatchServlet(
            Dispatch dispatch,
            Provider<CurrentLang> currentLangProvider,
            I18nStore_Server serverI18n,
            MultiLangDescStore multiLangDescStore) {
            super(
                dispatch,
                currentLangProvider,
                serverI18n,
                multiLangDescStore,
                true,
                new ApplicationLangs() {

                    @Override
                    public Collection<String> getLangs() {
                        return Arrays.asList(CurrentLang.DEFAULT_LANG);
                    }
                });
        }

        private static final long serialVersionUID = 1L;

        @Override
        public void registerAdditionalI18nModules(
            I18nStore_Server serverI18n,
            Provider<CurrentLang> currentLangProvider) {}

        @Override
        public void registerAssists(DescriptorStore descStore) {}

        @Override
        public void setupDefaults() {}

        @Override
        public void doLogAction(Loggable loggable, HttpServletRequest request) {

        }

    }

    private class DispatchAsyncMock implements DispatchAsync {
        @SuppressWarnings("unchecked")
        @Override
        public <A extends Action<R>, R extends Result> void execute(
            A action,
            AsyncCallback<R> callback) {
            try {
                callback.onSuccess((R) dispatchServlet.execute(action));
            } catch (DispatchException e) {
                callback.onFailure(e);
            }
        }

    }

    public class IneDispatchMock extends IneDispatch {

        private IneDispatchMock() {
            super(
                new DispatchAsyncMock(),
                new SilentStatusIndicator(),
                new SimpleEventBus(),
                new DefaultFailedHandler());
        }
    }

    public static IneDispatchMock getIneDispatchMock() {
        DispatchMock dM = new DispatchMock();
        return dM.new IneDispatchMock();
    }
}
