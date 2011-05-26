package com.inepex.ineForm.client.form.panelwidgets;


import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;

public class TabPageWidget extends PanelWidget{

	private final Widget titleWidget;
	private final FlowPanel content=new FlowPanel();
	
	public TabPageWidget(PanelWidgetRDesc descriptor, PanelWidget parent, String title, DisplayedFormUnitChangeHandler parentHandler) {
		super(descriptor, parent, parentHandler);
		initWidget(content);
		
		if(title!=null) {
			this.titleWidget=new Label(title);
		} else {
			this.titleWidget=new Label(" TAB ");
		}
	}

	@Override
	public void addToPanel(Widget w) {
		content.add(w);
	}

	public Widget getTitleWidget() {
		return titleWidget;
	}
}
