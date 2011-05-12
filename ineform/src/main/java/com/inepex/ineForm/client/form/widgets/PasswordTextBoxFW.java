package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineom.shared.descriptor.FDesc;

public class PasswordTextBoxFW extends TextBoxFWBase {

	public PasswordTextBoxFW(FDesc fielddescriptor) {
		super(fielddescriptor);
		textBox= new PasswordTextBox();
		textBox.setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
		initWidget(textBox);
	}
}
