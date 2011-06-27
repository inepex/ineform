package com.inepex.ineFrame.client.page.defaults;

import com.google.gwt.user.client.ui.Label;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class DummyPage extends FlowPanelBasedPage{

	@Override
	protected void onShow(boolean isFirstShow) {
		if(isFirstShow)
			mainPanel.add(new Label("not implemented yet..."));
	}
}
