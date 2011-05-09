package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.inepex.ineom.shared.descriptor.FDesc;

public class PlaceholderFW extends DenyingFormWidget {

	final FlowPanel flowPanel = new FlowPanel();
	
	public PlaceholderFW(FDesc fielddescriptor) {
		super(fielddescriptor);
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return true;
	}
	
}
