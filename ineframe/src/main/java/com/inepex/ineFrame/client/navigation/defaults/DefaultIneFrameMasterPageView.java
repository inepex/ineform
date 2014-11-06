package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader;
import com.inepex.ineFrame.client.navigation.header.IneFrameHeader.View;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanelWidget;
import com.inepex.ineFrame.client.util.DesignConstants;
import com.inepex.ineFrame.client.widget.ClickableFlowPanel;
import com.inepex.ineFrame.shared.IneformAsyncCallback;

@Singleton
public class DefaultIneFrameMasterPageView extends FlowPanel implements DefaultIneFrameMasterPage.View {
	
	
	private static final int borderWidth = 3;
	
	private MessagePanelWidget messagePanel;
	private ResizeLayoutPanel headerAndPageRoot;
	private ClickableFlowPanel clickHandlerLayout;
	private LayoutPanel headerAndPage;
	private NavigationDrawer navigationDrawer;
	private View header;

	@Inject
	DefaultIneFrameMasterPageView(IneFrameHeader.View header, MenuRenderer.View menu, MessagePanelWidget messagePanel, NavigationDrawer navigationDrawer) {
		this.header = header;
		this.navigationDrawer = navigationDrawer;
		this.messagePanel = messagePanel;
		setBase();
		
		messagePanel.getElement().setId("MessagePanel");
		messagePanel.addStyleName(Res.INST.get().style().MessagePanel());
		getElement().setId("MasterPage");
		setSize("100%", "100%");
		setStyleName(Res.INST.get().style().MasterPage());
		
		clickHandlerLayout = new ClickableFlowPanel();
		clickHandlerLayout.getElement().setId("ClickHandler");
		clickHandlerLayout.setSize("100%", "100%");
		
		clickHandlerLayout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeNavigationDrawer();
			}
		});
		
		headerAndPageRoot = new ResizeLayoutPanel();
		headerAndPageRoot.getElement().setId("HeaderAndPageRoot");
		headerAndPageRoot.setSize("100%", "100%");
		headerAndPageRoot.setStyleName(Res.INST.get().style().HeaderAndPageRoot());
		
		headerAndPage = new LayoutPanel();
		headerAndPage.getElement().setId("HeaderAndPage");
		headerAndPage.setStyleName(Res.INST.get().style().HeaderAndPage());
		
		headerAndPage.add(header.asWidget());
		headerAndPage.add(menu.asWidget());
		header.asWidget().asWidget().getElement().setId("Header");
		menu.asWidget().asWidget().getElement().setId("Menu");
		
		headerAndPage.setWidgetTopHeight(header.asWidget(), 0, Unit.PX, DesignConstants.base() + borderWidth, Unit.PX);
		headerAndPage.setWidgetTopBottom(menu.asWidget(), DesignConstants.base(), Unit.PX, 0, Unit.PX);
		
		headerAndPageRoot.setWidget(headerAndPage);
		
		navigationDrawer.asWidget().setSize(DesignConstants.b5WithUnit(), "100%");
		navigationDrawer.asWidget().getElement().setId("NavigationDrawer");
		navigationDrawer.asWidget().addStyleName(Res.INST.get().style().NavigationDrawer());
		
		add(messagePanel);
		add(navigationDrawer);
		add(headerAndPageRoot);
		

	}
	
	public void showMessage(String message, boolean isError) {
		messagePanel.showMessage(message, isError, 0);
	}
	
	private void setBase() {
		Window.Navigator.getUserAgent();
		String userAgent = Navigator.getUserAgent();
		if(userAgent.toLowerCase().contains("mobile") || Navigator.getPlatform().toLowerCase().contains("iphone")){
			DesignConstants.setBase((int)(DesignConstants.base()*3));
		}
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
		headerAndPage.add(clickHandlerLayout);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				clickHandlerLayout.setStyleName(Res.INST.get().style().ClickHandlerLayoutFaded());
				navigationDrawer.onDisplayed();
			}
		});
	}
	
	public void closeNavigationDrawer() {
		headerAndPageRoot.removeStyleName(Res.INST.get().style().navigationDrawerOpened());
		headerAndPageRoot.addStyleName(Res.INST.get().style().navigationDrawerClosed());
		clickHandlerLayout.setStyleName(Res.INST.get().style().ClickHandlerLayout());
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				headerAndPage.remove(clickHandlerLayout);
				navigationDrawer.onHidden();
			}
		};
		timer.schedule((int) (DesignConstants.defaultAnimationLength()*1000));
		header.toggleMenuIcon(isNavigationDrawerOpen());
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
		messagePanel.addStyleName(Res.INST.get().style().messagePanelOpened());
	}
	
	public void hideMessagePanel() {
		messagePanel.removeStyleName(Res.INST.get().style().messagePanelOpened());
		messagePanel.addStyleName(Res.INST.get().style().messagePanelClosed());
	}
	
	public boolean isMessagePanelShown() {
		return messagePanel.getStyleName().contains(Res.INST.get().style().messagePanelOpened());
	}
	
	@Override
	public void enableAliasMode(IneformAsyncCallback<Void> callback, String name, String email){
		navigationDrawer.enableAliasMode(callback, name, email);
		header.enableAliasMode();
	}
	
	@Override
	public void disableAliasMode(IneformAsyncCallback<Void> callback, String name, String email){
		navigationDrawer.disableAliasMode(callback, name, email);
		header.disableAliasMode();
	}
}
