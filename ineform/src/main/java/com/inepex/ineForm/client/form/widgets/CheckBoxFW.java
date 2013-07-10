package com.inepex.ineForm.client.form.widgets;

import com.inepex.ineForm.client.general.IneCheckBox;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class CheckBoxFW extends IneCheckBox {

	public static final String CHECKBOXTEXT = "checkBoxText";
	public static final String CHECKBOXHTML = "checkBoxHTML";
	
	public CheckBoxFW(FDesc fielddescriptor, WidgetRDesc wrDesc) {
		super(fielddescriptor, wrDesc);
	}
}
