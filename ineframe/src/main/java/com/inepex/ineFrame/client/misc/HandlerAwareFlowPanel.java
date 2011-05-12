package com.inepex.ineFrame.client.misc;

import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

public class HandlerAwareFlowPanel extends FlowPanel {
    private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

    /**
     * Any {@link HandlerRegistration}s added will be removed when
     * {@link #onDetach()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     *
     * @param handlerRegistration The registration.
     */
    public void registerHandler(HandlerRegistration handlerRegistration ) {
        handlerRegistrations.add( handlerRegistration );
    }
    
    @Override
    protected void onDetach() {
        for ( HandlerRegistration reg : handlerRegistrations ) {
        	if (reg != null)
        		reg.removeHandler();
        }
        handlerRegistrations.clear();
        super.onDetach();
    }
}
