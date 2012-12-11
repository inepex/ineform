package com.inepex.ineFrame.client.misc;

import java.util.List;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

/**
 * this abstract class is for manage handlerregistrations (auto remove on detach)
 *
 */
public abstract class HandlerAwareComposite extends Composite {
    private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

    /**
     * Any {@link HandlerRegistration}s added will be removed when
     * {@link #onDetach()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     *
     * @param handlerRegistration The registration.
     */
    protected void registerHandler(HandlerRegistration handlerRegistration ) {
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
