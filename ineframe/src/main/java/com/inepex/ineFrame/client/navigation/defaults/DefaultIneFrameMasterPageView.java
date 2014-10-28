package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;

@Singleton
public class DefaultIneFrameMasterPageView extends FlowPanel implements DefaultIneFrameMasterPage.View{

	@Inject
	DefaultIneFrameMasterPageView(IneFrameHeader.View header, MenuRenderer.View menu) {
	
		Notification notification = new DefaultIneFrameNotification();
		notification.setContent(new Label("test"));
		//add(notification.asWidget());
		add(header.asWidget());
		add(menu.asWidget());
	}

}
