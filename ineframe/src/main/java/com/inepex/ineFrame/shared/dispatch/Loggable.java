package com.inepex.ineFrame.shared.dispatch;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface Loggable extends Serializable, IsSerializable {
    String getActionName();

    String getActionParamteres();
}
