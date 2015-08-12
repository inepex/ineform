package com.inepex.ineFrame.client.pushedevents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import net.customware.gwt.dispatch.shared.Result;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.Timer;
import com.inepex.ineFrame.client.async.IneDispatchBase.PushedActionContext;
import com.inepex.ineFrame.server.DispatchMock;
import com.inepex.ineFrame.server.DispatchMock.Action1;
import com.inepex.ineFrame.server.DispatchMock.Action2;
import com.inepex.ineFrame.server.DispatchMock.ActionThrowsException;
import com.inepex.ineFrame.server.DispatchMock.Result1;
import com.inepex.ineFrame.server.DispatchMock.Result2;
import com.inepex.ineFrame.server.DispatchMock.Result3;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;

public class PushedEventProviderTest extends DefaultIneFrameClientSideTestBase {

    private int action1CallbackCallTimes = 0;
    PushedEventProvider pep;

    @Before
    public void init() {
        action1CallbackCallTimes = 0;
        GWTMockUtilities.disarm();
        pep = new PushedEventProvider(DispatchMock.getIneDispatchMock(), null);
        pep.eventQueryTimer = mock(Timer.class);

        // NOTE: Adding the first action calls the queryEvent() function
        pep.addAction(new Action1(), new Action1Context());
    }

    private class Action1Context extends PushedActionContext<Result1> {
        @Override
        public void onCastedSuccess(Result1 castedResult) {
            assertNotNull(castedResult);
            action1CallbackCallTimes++;
        }
    }

    @Test
    public void testPushedEventProvider() {

        @SuppressWarnings("unchecked")
        PushedActionContext<Result1> action1CallbackMock = mock(PushedActionContext.class);
        @SuppressWarnings("unchecked")
        PushedActionContext<Result2> action2CallbackMock = mock(PushedActionContext.class);
        pep.addAction(new Action1(), action1CallbackMock);
        pep.addAction(new Action2(), action2CallbackMock);

        pep.queryEvents();

        assertEquals(1, action1CallbackCallTimes);
        InOrder inOrder = inOrder(action1CallbackMock, action2CallbackMock);

        inOrder.verify(action1CallbackMock).onSuccess(any(Result.class));
        inOrder.verify(action2CallbackMock).onSuccess(any(Result.class));

    }

    @Test
    public void testBreakOnException() {
        @SuppressWarnings("unchecked")
        PushedActionContext<Result3> actionThrExCallack = mock(PushedActionContext.class);
        pep.addAction(new ActionThrowsException(), actionThrExCallack);

        // Call queryEvents 5 times. The last 2 calls should return at beginning
        // because of errors.
        pep.queryEvents();
        pep.queryEvents();
        pep.queryEvents();

        assertEquals(true, pep.stoppedOnFailures);
        assertEquals(3, pep.failureCount);

        pep.queryEvents(); // This resets the inner state, but does not call
                           // actions
        assertEquals(false, pep.stoppedOnFailures);
        assertEquals(0, pep.failureCount);

        assertEquals(3, action1CallbackCallTimes);

        verify(actionThrExCallack, times(3)).onFailure(any(Throwable.class));

        // schedule was also called when adding first action
        verify(pep.eventQueryTimer, times(4)).schedule(anyInt());

    }
}
