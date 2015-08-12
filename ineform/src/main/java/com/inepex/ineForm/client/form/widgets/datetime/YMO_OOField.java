package com.inepex.ineForm.client.form.widgets.datetime;

import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;

class YMO_OOField extends AbstractField {

    public YMO_OOField(
        IneDateGWT date,
        boolean showstepbuttons,
        int stepcount,
        boolean usetextbox,
        DateTimeFieldParentInterface parent,
        boolean enableselectmanager) {
        super(
            date,
            Precision.YMO_OO,
            showstepbuttons,
            stepcount,
            usetextbox,
            parent,
            enableselectmanager);
    }

}
