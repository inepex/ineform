package com.inepex.ineom.shared.descriptor.fdesc;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;

public class BooleanFDesc extends FDesc implements Serializable, IsSerializable {

    /**
	 *
	 */
    private static final long serialVersionUID = -2885140949910002444L;

    public BooleanFDesc() {
        type = IneT.BOOLEAN;
    }

    public BooleanFDesc(String key, String defaultDisplayName) {
        super(key, IneT.BOOLEAN, defaultDisplayName);
    }

}
