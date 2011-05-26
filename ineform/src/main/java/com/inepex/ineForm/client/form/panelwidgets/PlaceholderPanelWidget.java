package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineForm.shared.types.PanelWidgetT;

public class PlaceholderPanelWidget extends PanelWidget {

	private final FlowPanel mainPanel = new FlowPanel();
	private final FlowPanel placeholder = new FlowPanel();
	private final FlowPanel formPanel = new FlowPanel();
	private InlineHTML title;
	
	public PlaceholderPanelWidget(PanelWidgetRDesc descriptor, PanelWidget parent, PanelWidgetRDesc paneldesc, DisplayedFormUnitChangeHandler handler) {
		super(descriptor, parent, handler);
		initWidget(mainPanel);
		mainPanel.add(placeholder);
		mainPanel.add(formPanel);
		
		String htmlTitle = paneldesc.getPropValue(PanelWidgetT.p_TitleHtml);
		
		if (htmlTitle != null){
			title = new InlineHTML(htmlTitle);
			placeholder.add(title);
		}
			
	}

	@Override
	public void addToPanel(Widget w) {
		formPanel.add(w);
	}

	public FlowPanel getPlaceholder() {
		return placeholder;
	}
	
	public void setTitleHtml(String html){
		title.setHTML(html);
	}
}
