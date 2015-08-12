package com.inepex.ineom.shared.descriptor.fdesc;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;

public class DoubleFDesc extends NumericFDesc implements Serializable, IsSerializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1103467918896800026L;

    public DoubleFDesc() {
        type = IneT.DOUBLE;
    }

    public DoubleFDesc(String key, String defaultDisplayName) {
        super(key, IneT.DOUBLE, defaultDisplayName);
    }
}
