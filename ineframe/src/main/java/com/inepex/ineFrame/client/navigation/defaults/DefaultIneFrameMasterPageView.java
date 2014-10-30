package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanelWidget;

@Singleton
public class DefaultIneFrameMasterPageView extends FlowPanel implements DefaultIneFrameMasterPage.View{

	@Inject
	DefaultIneFrameMasterPageView(IneFrameHeader.View header, MenuRenderer.View menu, MessagePanelWidget messagePanel) {
		ResizeLayoutPanel layoutPanel1 = new ResizeLayoutPanel();
		ResizeLayoutPanel layoutPanel2 = new ResizeLayoutPanel();
		layoutPanel1.add(header.asWidget());
		layoutPanel1.setHeight("53PX");
		layoutPanel2.add(menu.asWidget());
		layoutPanel2.setHeight("400PX");
		add(messagePanel);
		messagePanel.setVisible(false);
		add(layoutPanel1);
		add(layoutPanel2);
		//messagePanel.setParent(this);
		messagePanel.showMessage("fadsfasdf", false);
	}
}
