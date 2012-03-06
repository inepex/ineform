package com.inepex.ineForm.client.form.widgets;

import com.google.gwt.user.client.ui.FlowPanel;

public class PlaceForSomethingFW extends DenyingFormWidget {

	private final FlowPanel place = new FlowPanel();
	
	public PlaceForSomethingFW() {
		super(null);
		initWidget(place);
	}
	
	@Override
	public boolean isReadOnlyWidget() {
		return true;
	}
	
	public FlowPanel getPlace() {
		return place;
	}
}
