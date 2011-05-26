package com.inepex.ineForm.client.form.panelwidgets;
	
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
	
public class VerticalPanelWidget extends PanelWidget{
	
	private final VerticalPanel mainPanel=new VerticalPanel();
	
	public VerticalPanelWidget(PanelWidgetRDesc descriptor,PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		super(descriptor ,parent, handler);
		initWidget(mainPanel);
	}
	
	@Override
	public void addToPanel(Widget w) {
		mainPanel.add(w);
	}
}