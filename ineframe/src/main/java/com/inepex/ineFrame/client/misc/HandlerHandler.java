package com.inepex.ineFrame.client.misc;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import jakarta.inject.Inject;

public class HandlerHandler {

    @Inject
    public HandlerHandler() {}

    private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

    public void registerHandler(HandlerRegistration handlerRegistration) {
        handlerRegistrations.add(handlerRegistration);
    }

    public void unregister() {
        for (HandlerRegistration reg : handlerRegistrations) {
            if (reg != null)
                reg.removeHandler();
        }
        handlerRegistrations.clear();
    }

}
