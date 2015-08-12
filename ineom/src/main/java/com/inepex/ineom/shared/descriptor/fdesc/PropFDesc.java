package com.inepex.ineom.shared.descriptor.fdesc;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;

public class PropFDesc extends FDesc implements Serializable, IsSerializable {

    private static final long serialVersionUID = 6454028745265496800L;
    private String id;

    public PropFDesc() {
        type = IneT.PROP;
    }

    public PropFDesc(String key, String defaultDisplayName, String id) {
        super(key, IneT.PROP, defaultDisplayName);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
