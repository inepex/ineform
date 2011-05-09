package com.inepex.ineForm.client.form.panelwidgets;
	
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.types.PanelWidgetT;
	
public class VerticalPanelWidget extends PanelWidget{
	
	private final VerticalPanel mainPanel=new VerticalPanel();
	
	public VerticalPanelWidget(PanelWidgetT type,PanelWidget parent, DisplayedFormUnitChangeHandler handler) {
		super(type,parent, handler);
		initWidget(mainPanel);
	}
	
	@Override
	public void addToPanel(Widget w) {
		mainPanel.add(w);
	}
}