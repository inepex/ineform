package com.inepex.ineForm.client.datamanipulator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

public class RightSideButtonsPanel extends HandlerAwareComposite {

	private static RightSideButtonsPanelUiBinder uiBinder = GWT.create(RightSideButtonsPanelUiBinder.class);

	interface RightSideButtonsPanelUiBinder extends UiBinder<Widget, RightSideButtonsPanel> {
	}
	
	@UiField
	FlowPanel buttonPanel;
	@UiField
	FlowPanel centerPanel;

	public RightSideButtonsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FlowPanel getButtonPanel() {
		return buttonPanel;
	}

	public FlowPanel getCenterPanel() {
		return centerPanel;
	}

}
