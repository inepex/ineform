package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class PasswordTextBoxFW extends TextBoxFWBase {

    public PasswordTextBoxFW(FDesc fielddescriptor, WidgetRDesc wrDesc) {
        super(fielddescriptor);
        textBox = new PasswordTextBox();
        updateWidth(wrDesc);
        initWidget(textBox);
    }
}
