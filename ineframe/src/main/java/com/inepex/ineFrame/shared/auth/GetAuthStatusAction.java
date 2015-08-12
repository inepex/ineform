package com.inepex.ineFrame.shared.auth;

import net.customware.gwt.dispatch.shared.Action;

public class GetAuthStatusAction implements Action<AuthStatusResultBase> {

    private String userEmail;
    private String userUUID;

    public GetAuthStatusAction() {

    }

    public GetAuthStatusAction(String userEmail, String userUUID) {
        super();
        this.userEmail = userEmail;
        this.userUUID = userUUID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

}
