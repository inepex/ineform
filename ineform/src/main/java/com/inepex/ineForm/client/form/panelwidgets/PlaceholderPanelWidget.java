package com.inepex.ineForm.client.form.panelwidgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;

public class PlaceholderPanelWidget extends PanelWidget {

	public final static String p_TitleHtml = "TitleHtml";
	
	private final FlowPanel mainPanel = new FlowPanel();
	private final FlowPanel placeholder = new FlowPanel();
	private final FlowPanel formPanel = new FlowPanel();
	private HTML title;
	
	public PlaceholderPanelWidget(PanelWidgetRDesc descriptor, PanelWidget parent, PanelWidgetRDesc paneldesc, DisplayedFormUnitChangeHandler handler) {
		super(descriptor, parent, handler);
		initWidget(mainPanel);
		mainPanel.add(placeholder);
		mainPanel.add(formPanel);
		
		String htmlTitle = paneldesc.getPropValue(p_TitleHtml);
		
		if (htmlTitle != null){
			FlowPanel fp = new FlowPanel();
			fp.setStyleName(ResourceHelper.ineformRes().style().placeHolderTitlePanel());
			title = new HTML(htmlTitle);
			fp.add(title);
			placeholder.add(fp);
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
