package com.inepex.ineFrame.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.server.util.TimeZoneEnum;
import com.inepex.ineFrame.shared.GetTimeZoneNamesAction;
import com.inepex.ineFrame.shared.GetTimeZoneNamesResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public class GetTimeZoneNamesHandler
    extends
    AbstractIneHandler<GetTimeZoneNamesAction, GetTimeZoneNamesResult> {

    @Override
    protected GetTimeZoneNamesResult doExecute(
        GetTimeZoneNamesAction action,
        ExecutionContext context) throws AuthenticationException, DispatchException {
        return new GetTimeZoneNamesResult(TimeZoneEnum.getValueRange());
    }

    @Override
    public Class<GetTimeZoneNamesAction> getActionType() {
        return GetTimeZoneNamesAction.class;
    }
}
