package com.inepex.ineFrame.shared;

import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class PingResult extends GenericActionResult {

    private boolean sessionAlive = false;

    public PingResult() {}

    public boolean isSessionAlive() {
        return sessionAlive;
    }

    public void setSessionAlive(boolean sessionAlive) {
        this.sessionAlive = sessionAlive;
    }

}
