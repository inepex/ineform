package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.TextBox;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class TextBoxFW extends TextBoxFWBase {

	public TextBoxFW(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		super(fielddescriptor);
		textBox= new TextBox();
		updateWidth(wrDesc);
		initWidget(textBox);
	}
}
