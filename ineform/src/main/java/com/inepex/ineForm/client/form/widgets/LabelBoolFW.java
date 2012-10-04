package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class LabelBoolFW extends DenyingFormWidget {
	
	private final InlineLabel lbl= new InlineLabel();
	private final String trueString;
	private final String falseString;
	
	private Boolean val;

	public LabelBoolFW(FDesc fieldDescriptor, String trueString, String falseString) {
		super(fieldDescriptor);
		initWidget(lbl);
		this.trueString=trueString;
		this.falseString=falseString;
	}
	
	@Override
	public boolean handlesBoolean() {
		return true;
	}

	@Override
	public void setBooleanValue(Boolean value) {
		val=value;
		if(val==null) lbl.setText("");
		else if(val) lbl.setText(trueString);
		else lbl.setText(falseString);
	}

	@Override
	public Boolean getBooleanValue() {
		return val;
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return true;
	}
}
