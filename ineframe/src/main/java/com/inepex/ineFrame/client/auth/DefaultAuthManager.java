package com.inepex.ineFrame.client.auth;

import com.google.gwt.event.shared.EventBus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.inject.Provider;
import com.inepex.ineFrame.client.async.IneDispatch;

@Singleton
public class DefaultAuthManager extends AbstractAuthManager {

    @Inject
    public DefaultAuthManager(Provider<IneDispatch> dispatcher, EventBus eventBus) {
        super(dispatcher, eventBus);
    }

}
