package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanelWidget;
import com.inepex.ineFrame.client.util.DesignConstants;

@Singleton
public class DefaultIneFrameMasterPageView extends FlowPanel implements DefaultIneFrameMasterPage.View {
	
	private MessagePanelWidget messagePanel;
	private ResizeLayoutPanel headerAndPageRoot;

	@Inject
	DefaultIneFrameMasterPageView(IneFrameHeader.View header, MenuRenderer.View menu, MessagePanelWidget messagePanel, NavigationDrawer navigationDrawer) {
		this.messagePanel = messagePanel;
		
		getElement().setId("MasterPage");
		setSize("100%", "100%");
		setStyleName(Res.INST.get().style().MasterPage());
		
		headerAndPageRoot = new ResizeLayoutPanel();
		headerAndPageRoot.getElement().setId("HeaderAndPageRoot");
		headerAndPageRoot.setSize("100%", "100%");
		headerAndPageRoot.setStyleName(Res.INST.get().style().HeaderAndPageRoot());
		
		LayoutPanel headerAndPage = new LayoutPanel();
		headerAndPage.getElement().setId("HeaderAndPage");
		headerAndPage.setStyleName(Res.INST.get().style().HeaderAndPage());
		
		headerAndPage.add(header.asWidget());
		headerAndPage.add(menu.asWidget());
		header.asWidget().asWidget().getElement().setId("Header");
		menu.asWidget().asWidget().getElement().setId("Menu");
		
		headerAndPage.setWidgetTopHeight(header.asWidget(), 0, Unit.PX, DesignConstants.base, Unit.PX);
		headerAndPage.setWidgetTopBottom(menu.asWidget(), DesignConstants.base, Unit.PX, 0, Unit.PX);
		
		headerAndPageRoot.setWidget(headerAndPage);
		
		navigationDrawer.asWidget().setSize(DesignConstants.b5(), "100%");
		navigationDrawer.asWidget().getElement().setId("NavigationDrawer");
		navigationDrawer.asWidget().addStyleName(Res.INST.get().style().NavigationDrawer());
		
		add(messagePanel);
		add(navigationDrawer);
		add(headerAndPageRoot);	
	}
	
	public void toggleNavigationDrawer() {
		if (isNavigationDrawerOpen()) {
			closeNavigationDrawer();
		} else {
			openNavigationDrawer();
		}
	}
	
	public void openNavigationDrawer() {
		headerAndPageRoot.removeStyleName(Res.INST.get().style().navigationDrawerClosed());
		headerAndPageRoot.addStyleName(Res.INST.get().style().navigationDrawerOpened());
	}
	
	public void closeNavigationDrawer() {
		headerAndPageRoot.removeStyleName(Res.INST.get().style().navigationDrawerOpened());
		headerAndPageRoot.addStyleName(Res.INST.get().style().navigationDrawerClosed());
	}
	
	public boolean isNavigationDrawerOpen() {
		return headerAndPageRoot.getStyleName().contains(Res.INST.get().style().navigationDrawerOpened());
	}
	
	public void toggleMessagePanel() {
		if (isMessagePanelShown()) {
			hideMessagePanel();
		} else {
			showMessagePanel();
		}
	}
	
	public void showMessagePanel() {
		messagePanel.removeStyleName(Res.INST.get().style().messagePanelClosed());
		messagePanel.setStyleName(Res.INST.get().style().messagePanelOpened());
	}
	
	public void hideMessagePanel() {
		messagePanel.removeStyleName(Res.INST.get().style().messagePanelOpened());
		messagePanel.setStyleName(Res.INST.get().style().messagePanelClosed());
	}
	
	public boolean isMessagePanelShown() {
		return messagePanel.getStyleName().contains(Res.INST.get().style().messagePanelOpened());
	}
}
