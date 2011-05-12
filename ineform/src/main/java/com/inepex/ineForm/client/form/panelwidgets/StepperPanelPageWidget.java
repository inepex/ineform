package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class StepperPanelPageWidget extends PanelWidget{

	private final FlowPanel mainPanel=new FlowPanel();
	
	public StepperPanelPageWidget(PanelWidgetT type,PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		super(type, parent, handler);
		initWidget(mainPanel);
	}

	@Override
	public void addToPanel(Widget w) {
		mainPanel.add(w);
	}
	
}
