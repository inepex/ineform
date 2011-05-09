package com.inepex.ineFrame.shared.dispatch;

import java.io.Serializable;

public interface Loggable extends Serializable {
	String getActionName();
	String getActionParamteres();
}
