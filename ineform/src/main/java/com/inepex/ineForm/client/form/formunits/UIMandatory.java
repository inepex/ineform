package com.inepex.ineForm.client.form.formunits;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

public class UIMandatory extends HTML implements IsWidget {
    UIMandatory(boolean mandatory) {
        if (mandatory)
            setHTML("*");

        setStyleName("mandatorySign");
    }
}