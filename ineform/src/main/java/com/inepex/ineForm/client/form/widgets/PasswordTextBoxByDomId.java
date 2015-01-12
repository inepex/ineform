package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class PasswordTextBoxByDomId extends TextBoxFWBase{

	public PasswordTextBoxByDomId(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		super(fielddescriptor);
		String domId = wrDesc.getPropValue(IneFormProperties.domId);
		if(domId == null) return;
		Element element = DOM.getElementById(domId);
		textBox = element == null ? new PasswordTextBox() : PasswordTextBox.wrap(element);
		updateWidth(wrDesc);
		initWidget(textBox);
		textBox.addStyleName(ResourceHelper.ineformRes().style().textBoxFW());
	}

	
}
