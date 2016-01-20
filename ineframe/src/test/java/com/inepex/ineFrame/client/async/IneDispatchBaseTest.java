package com.inepex.ineFrame.client.async;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.inepex.ineFrame.client.async.IneDispatchBase.IneAsyncCallback;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineFrame.shared.GetDescStore;
import com.inepex.ineFrame.shared.GetDescStoreResult;

public class IneDispatchBaseTest {

    public class TestDispatcher extends IneDispatchBase<Action<? extends Result>> {

        public TestDispatcher(ConnectionFailedHandler connectionFailedHandler, EventBus eventBus) {
            super(connectionFailedHandler, eventBus);
        }

        @Override
        protected void doExecute(Action<?> action, IneAsyncCallback callback) {}

    }

    private EventBus eventBus;
    private ConnectionFailedHandler connectionFailedHandler;
    private TestDispatcher dispatcher;

    private GetDescStoreResult result = new GetDescStoreResult();

    @Test
    public void failureTest() {
        eventBus = new SimpleEventBus();
        connectionFailedHandler = Mockito.mock(ConnectionFailedHandler.class);
        dispatcher = Mockito.spy(new TestDispatcher(connectionFailedHandler, eventBus));
        dispatcher.setStatusIndicator(Mockito.mock(AsyncStatusIndicator.class));

        Mockito.when(connectionFailedHandler.startRecover()).thenReturn(true);

        SuccessCallback<GetDescStoreResult> callback = Mockito.mock(SuccessCallback.class);

        dispatcher.execute(new GetDescStore(), callback);

        ArgumentCaptor<IneAsyncCallback> asyncCallbackCaptor = ArgumentCaptor
            .forClass(IneAsyncCallback.class);
        Mockito
            .verify(dispatcher)
            .doExecute(Mockito.any(Action.class), asyncCallbackCaptor.capture());
        asyncCallbackCaptor.getValue().onFailure(new StatusCodeException(0, ""));

        dispatcher.onEvent(new ConnectionEvent(false));
        asyncCallbackCaptor.getValue().onSuccess(result);

        Mockito.verify(callback).onSuccess(result);

    }

}
