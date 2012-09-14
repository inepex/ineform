package com.inepex.ineom.shared.dispatch;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum ManipulationTypes implements IsSerializable  {
		CREATE_OR_EDIT_REQUEST,
		DELETE,
		UNDELETE,
		REFRESH
}
