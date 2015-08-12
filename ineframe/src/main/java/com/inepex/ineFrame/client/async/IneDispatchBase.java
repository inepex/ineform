package com.inepex.ineFrame.client.async;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.inject.Provider;
import com.inepex.ineFrame.client.async.ConnectionEvent.ConnectionEventHandler;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineFrame.shared.exceptions.AuthorizationException;
import com.inepex.ineFrame.shared.exceptions.PageNotFoundException;

public abstract class IneDispatchBase<A> implements ConnectionEventHandler {

    protected class IneAsyncCallback<R> implements AsyncCallback<R> {

        protected final SuccessCallback<R> successCallback;
        protected final AsyncStatusIndicator statusIndicator;
        protected CommandExecution<A> execution;

        public IneAsyncCallback(
            SuccessCallback<R> successCallback,
            AsyncStatusIndicator statusIndicator,
            CommandExecution<A> execution) {
            this.successCallback = successCallback;
            this.statusIndicator = statusIndicator;
            this.execution = execution;
        }

        @Override
        public void onFailure(Throwable caught) {
            caught.printStackTrace();
            if (isInvalid()) {
                pendingExecutions.remove(execution);
                statusIndicator.onSuccess(null);
                return;
            }
            if (caught instanceof AuthorizationException) {
                PlaceRequestEvent pre = new PlaceRequestEvent();
                pre.setHierarchicalTokensWithParam(NavigationProperties.noRightPlace);
                eventBus.fireEvent(pre);
                successCallback.onFailure(caught);
                statusIndicator.onSuccess("");
                return;
            } else if (caught instanceof AuthenticationException) {
                startSwallowStatuscodeException();
                connectionFailedHandler.shutdown();
                Window.Location.reload();
                return;
            } else if (caught instanceof PageNotFoundException) {
                PlaceRequestEvent pre = new PlaceRequestEvent();
                pre.setHierarchicalTokensWithParam(NavigationProperties.pageNotFoundPlace);
                eventBus.fireEvent(pre);
                successCallback.onFailure(caught);
                statusIndicator.onSuccess("");
                return;
            } else if (caught instanceof StatusCodeException) {
                if (swallowStatusCodeException) {
                    return;
                } else if (((StatusCodeException) caught).getStatusCode() == 0) {
                    if (connectionFailedHandler.startRecover()) {
                        return;
                    }
                }
            }

            pendingExecutions.remove(execution);
            successCallback.onFailure(caught);
            statusIndicator.onGeneralFailure(caught.getMessage());
            caught.printStackTrace();
        }

        @Override
        public void onSuccess(R result) {
            pendingExecutions.remove(execution);
            statusIndicator.onSuccess("");
            if (isInvalid()) {
                return;
            }

            successCallback.onSuccess(result);
        }

        private boolean isInvalid() {
            return (placeHandler != null && execution.placeToken != null && !placeHandler
                .get()
                .getCurrentFullToken()
                .equals(execution.placeToken));
        }

        public SuccessCallback<R> getSuccessCallback() {
            return successCallback;
        }

        public AsyncStatusIndicator getStatusIndicator() {
            return statusIndicator;
        }

        public CommandExecution<A> getExecution() {
            return execution;
        }
    }

    public static abstract class SuccessCallback<R> {
        public void onFailure(Throwable caught) {

        }

        public abstract void onSuccess(R result);
    }

    public static abstract class PushedActionContext<R extends Result>
        extends
        SuccessCallback<Result> {
        public AsyncStatusIndicator customStatusIndicator = null;

        public PushedActionContext() {}

        public PushedActionContext(AsyncStatusIndicator customStatusIndicator) {
            this.customStatusIndicator = customStatusIndicator;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(Result result) {
            onCastedSuccess((R) result);
        }

        public abstract void onCastedSuccess(R castedResult);

        /**
         * Override this to get notified about when the action is invoked
         * 
         * @param action
         */
        public void onBeforeCallAction(Action<?> action) {

        }
    }

    @SuppressWarnings("rawtypes")
    protected class CommandExecution<A> {
        A command;
        SuccessCallback callback;
        AsyncStatusIndicator statusIndicator;
        String placeToken;

        public CommandExecution(
            A command,
            SuccessCallback callback,
            AsyncStatusIndicator statusIndicator) {
            super();
            this.command = command;
            this.callback = callback;
            this.statusIndicator = statusIndicator;
        }

        public void setPlaceToken(String token) {
            this.placeToken = token;
        }
    }

    protected AsyncStatusIndicator defaultStatusIndicator;
    protected EventBus eventBus;
    protected Provider<PlaceHandler> placeHandler;
    protected boolean swallowStatusCodeException = false;
    protected ConnectionFailedHandler connectionFailedHandler;
    protected Set<CommandExecution<A>> pendingExecutions = new HashSet<CommandExecution<A>>();

    public IneDispatchBase(ConnectionFailedHandler connectionFailedHandler, EventBus eventBus) {
        this.connectionFailedHandler = connectionFailedHandler;
        eventBus.addHandler(ConnectionEvent.TYPE, this);
    }

    public void startSwallowStatuscodeException() {
        swallowStatusCodeException = true;
    }

    public void setStatusIndicator(AsyncStatusIndicator statusIndicator) {
        defaultStatusIndicator = statusIndicator;
    }

    public AsyncStatusIndicator getDefaultStatusIndicator() {
        return defaultStatusIndicator;
    }

    @Override
    public void onEvent(ConnectionEvent e) {
        if (!e.isFailure())
            reconnected();
    }

    public <R> void execute(A action, SuccessCallback<R> callback) {
        execute(action, callback, false);
    }

    public <R> void execute(A action, SuccessCallback<R> callback, boolean bindToPlace) {
        this.execute(action, callback, defaultStatusIndicator, bindToPlace);
    }

    public <R> void execute(
        A action,
        SuccessCallback<R> callback,
        AsyncStatusIndicator statusIndicator) {
        execute(action, callback, statusIndicator, false);
    }

    public <R> void execute(
        A action,
        SuccessCallback<R> callback,
        AsyncStatusIndicator statusIndicator,
        boolean bindToPlace) {
        if (statusIndicator == null)
            statusIndicator = defaultStatusIndicator;
        CommandExecution<A> execution = new CommandExecution<A>(action, callback, statusIndicator);
        if (placeHandler != null && bindToPlace)
            execution.setPlaceToken(placeHandler.get().getCurrentFullToken());
        pendingExecutions.add(execution);
        executeInternal(execution);
    }

    protected <R> void executeInternal(CommandExecution<A> execution) {
        IneAsyncCallback<R> asyncCallback =
            new IneAsyncCallback<R>(execution.callback, execution.statusIndicator, execution);
        doExecute(execution.command, asyncCallback);
        execution.statusIndicator.onAsyncRequestStarted("");
    }

    public void reconnected() {
        if (pendingExecutions.size() != 0) {
            List<CommandExecution<A>> tmpList =
                new ArrayList<CommandExecution<A>>(pendingExecutions);
            for (CommandExecution<A> execution : tmpList) {
                execution.statusIndicator.onSuccess("");
                executeInternal(execution);
            }
        }
    }

    public void setPlaceHandler(Provider<PlaceHandler> placeHandler) {
        this.placeHandler = placeHandler;
    }

    protected abstract void doExecute(A action, IneAsyncCallback callback);

}
