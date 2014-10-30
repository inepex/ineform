package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanelWidget;
import com.inepex.ineFrame.client.util.DesignConstants;

@Singleton
public class DefaultIneFrameMasterPageView extends FlowPanel implements DefaultIneFrameMasterPage.View{

	@Inject
	DefaultIneFrameMasterPageView(IneFrameHeader.View header, MenuRenderer.View menu, MessagePanelWidget messagePanel) {
		getElement().setId("MasterPage");
		setSize("100%", "100%");
	
		messagePanel.setVisible(false);
		
		ResizeLayoutPanel resizeLayoutPanel = new ResizeLayoutPanel();
		resizeLayoutPanel.getElement().setId("ResizeLayout");
		resizeLayoutPanel.setSize("100%", "100%");
		
		LayoutPanel headerAndPage = new LayoutPanel();
		headerAndPage.getElement().setId("HeaderAndPage");
		
		headerAndPage.add(header.asWidget());
		headerAndPage.add(menu.asWidget());
		header.asWidget().asWidget().getElement().setId("Header");
		menu.asWidget().asWidget().getElement().setId("Menu");
		
		headerAndPage.setWidgetTopHeight(header.asWidget(), 0, Unit.PX, DesignConstants.base, Unit.PX);
		headerAndPage.setWidgetTopBottom(menu.asWidget(), DesignConstants.base, Unit.PX, 0, Unit.PX);
		
		resizeLayoutPanel.setWidget(headerAndPage);
		add(messagePanel);
		add(resizeLayoutPanel);
		
		messagePanel.showMessage("fadsfasdf", false);
	}
}
