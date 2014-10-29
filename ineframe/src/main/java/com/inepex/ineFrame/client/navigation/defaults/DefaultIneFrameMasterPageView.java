package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanel;

@Singleton
public class DefaultIneFrameMasterPageView extends FlowPanel implements DefaultIneFrameMasterPage.View{

	@Inject
	DefaultIneFrameMasterPageView(IneFrameHeader.View header, MenuRenderer.View menu, MessagePanel messagePanel) {
		//add();
		add(header.asWidget());
		add(menu.asWidget());
	}

}
