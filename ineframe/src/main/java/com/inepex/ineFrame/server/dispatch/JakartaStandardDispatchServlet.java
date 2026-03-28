package com.inepex.ineFrame.server.dispatch;

import com.google.gwt.user.server.rpc.jakarta.RemoteServiceServlet;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * Jakarta-compatible replacement for gwt-dispatch's GuiceStandardDispatchServlet.
 * Extends GWT 2.12.1's jakarta RemoteServiceServlet instead of the javax version.
 */
public class JakartaStandardDispatchServlet extends RemoteServiceServlet
        implements StandardDispatchService {

    private static final long serialVersionUID = 1L;

    private final Dispatch dispatch;

    public JakartaStandardDispatchServlet(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    @Override
    public Result execute(Action<?> action) throws DispatchException {
        return dispatch.execute(action);
    }

    protected Dispatch getDispatch() {
        return dispatch;
    }
}
