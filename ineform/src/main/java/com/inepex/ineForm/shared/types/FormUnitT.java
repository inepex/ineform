package com.inepex.ineForm.shared.types;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineForm.client.form.widgets.FormWidget;

/**
 * Defines names for different {@link FormWidget} types
 * 
 * 
 * @author istvan
 *
 */
public class FormUnitT implements Serializable, IsSerializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3542896731206796817L;

    public final static FormUnitT SIMPLETABLEFORM = new FormUnitT("SIMPLETABLEFORM");
    public final static FormUnitT UIBINDERFORM = new FormUnitT("UIBINDERFORM");

    private String typeName = "";

    public FormUnitT() {

    }

    public FormUnitT(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof FormUnitT)
            return ((FormUnitT) obj).getTypeName().equals(getTypeName());

        if (obj instanceof String)
            return ((String) obj).equals(getTypeName());

        return super.equals(obj);
    }

}
