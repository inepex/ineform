package com.inepex.ineForm.client.form.widgets.datetime;

import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;

class OOO_HMSField extends AbstractField {

    public OOO_HMSField(
        IneDateGWT date,
        boolean showstepbuttons,
        int stepcount,
        boolean usetextbox,
        DateTimeFieldParentInterface parent,
        boolean enableselectmanager) {
        super(
            date,
            Precision.OOO_HMS,
            showstepbuttons,
            stepcount,
            usetextbox,
            parent,
            enableselectmanager);
    }

}
