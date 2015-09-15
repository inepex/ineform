package com.inepex.ineFrame.shared.auth;

import net.customware.gwt.dispatch.shared.Action;

public class CaptchaInfoAction implements Action<CaptchaInfoResult> {

    private int type; // 0: login, 1: registration

    public CaptchaInfoAction() {}

    public CaptchaInfoAction(int type) {
        super();
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
