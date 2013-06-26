package com.inepex.ineFrame.client.navigation.header.widget;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;

public class SettingsPopup extends DialogBox {
	
	VerticalPanel panel;
	
	public SettingsPopup() {
		super(false, false);
		panel = new VerticalPanel();
		panel.setStyleName(ResourceHelper.getRes().style().settingsPopup());
		add(panel);
	}

	public void clearClickLabels() {
		panel.clear();
	}

	public void addClickLabel(ClickLabel clickLabel) {
		panel.add(clickLabel);
	}
	
}
