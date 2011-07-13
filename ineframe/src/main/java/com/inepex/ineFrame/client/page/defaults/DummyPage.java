package com.inepex.ineFrame.client.page.defaults;

import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class DummyPage extends FlowPanelBasedPage{
	
	private final String text;
	
	@Inject
	public DummyPage() {
		this("not implemented yet...");
	}
	
	public DummyPage(String text) {
		this.text=text;
	}

	@Override
	protected void onShow(boolean isFirstShow) {
		if(isFirstShow)
			mainPanel.add(new HTML(text));
	}
}
