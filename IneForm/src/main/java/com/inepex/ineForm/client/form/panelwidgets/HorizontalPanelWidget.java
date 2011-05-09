package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class HorizontalPanelWidget extends PanelWidget{

	private final HorizontalPanel mainPanel=new HorizontalPanel();
	
	public HorizontalPanelWidget(PanelWidgetT type, PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		super(type, parent, handler);
		initWidget(mainPanel);
	}

	@Override
	public void addToPanel(Widget w) {
		mainPanel.add(w);
	}
	
}
