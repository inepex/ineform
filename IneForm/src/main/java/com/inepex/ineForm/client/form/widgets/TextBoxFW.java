package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineom.shared.descriptor.FDesc;

public class TextBoxFW extends TextBoxFWBase {

	public TextBoxFW(FDesc fielddescriptor) {
		super(fielddescriptor);
		textBox= new TextBox();
		textBox.setWidth(IneFormProperties.DEFAULT_TextBoxWidth);
		initWidget(textBox);
	}
}
