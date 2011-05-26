package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;

public class HorizontalPanelWidget extends PanelWidget{

	private final HorizontalPanel mainPanel=new HorizontalPanel();
	
	public HorizontalPanelWidget(PanelWidgetRDesc descriptor, PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		super(descriptor, parent, handler);
		initWidget(mainPanel);
	}

	@Override
	public void addToPanel(Widget w) {
		mainPanel.add(w);
	}
	
}
