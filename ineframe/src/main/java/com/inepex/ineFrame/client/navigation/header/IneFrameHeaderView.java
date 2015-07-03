package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.util.DesignConstants;
import com.inepex.ineFrame.client.widget.ClickableFlowPanel;

@Singleton
public class IneFrameHeaderView extends HandlerAwareFlowPanel implements IneFrameHeader.View {
	
	private Widget logo;
	private ClickableFlowPanel menuIcon = new ClickableFlowPanel();
	private OnClickedLogic menuIconClickedLogic;
	private OnClickedLogic logoClickedLogic;
	private FlexTable menuTable = new FlexTable();
	protected AuthManager authManager;
	private boolean logoClickEnabled = false;

	
	@Inject
	public IneFrameHeaderView(
			HeaderViewLogo logoCreator, 
			AuthManager authManager) {
		this.authManager = authManager;
		logo = logoCreator.createLogo();
		buildStructure();
		setStyleNames();
	}
	
	private void buildStructure() {
		add(menuTable);
		menuTable.setWidget(0, 0, menuIcon);
		menuTable.setWidget(0, 1, logo);
		menuIcon.setVisible(authManager.isUserLoggedIn());
		menuIcon.getElement().setId(DesignConstants.appMenuIconId);
	}
	
	private void setStyleNames(){
		setStyleName(ResourceHelper.getRes().style().header());
		menuTable.setCellPadding(0);
		menuTable.setCellSpacing(0);
		menuTable.setStyleName(ResourceHelper.getRes().style().menuTable());
		menuTable.getFlexCellFormatter().setStyleName(0, 0, ResourceHelper.getRes().style().menuCell());
		menuTable.getFlexCellFormatter().setStyleName(0, 1, ResourceHelper.getRes().style().menuCell());
		menuTable.getFlexCellFormatter().setStyleName(0, 2, ResourceHelper.getRes().style().menuCell());
		menuTable.getFlexCellFormatter().setStyleName(0, 3, ResourceHelper.getRes().style().menuCell());
		menuTable.getFlexCellFormatter().setStyleName(0, 4, ResourceHelper.getRes().style().menuCell());
		menuIcon.addStyleName(ResourceHelper.getRes().style().menuIcon());
	}
	
	public void setApp(ImageResource img, String appName){
		
		Image breadCrumb = new Image(ResourceHelper.getRes().headerBreadcrumbIcon());
		
		FlowPanel appImageWrapper = new FlowPanel();
		Image appImage = new Image(img);
		appImageWrapper.addStyleName(ResourceHelper.getRes().style().menuCellImg());
		appImageWrapper.add(appImage);
		
		menuTable.setWidget(0, 2, breadCrumb);
		menuTable.setWidget(0, 3, appImageWrapper);
		menuTable.setText(0, 4, appName);
	}
	
	public void clearApp(){
		menuTable.clearCell(0, 2);
		menuTable.clearCell(0, 3);
		menuTable.clearCell(0, 4);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		registerHandler(menuIcon.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (menuIconClickedLogic != null) {
					menuIconClickedLogic.doLogic();
				}
			}
		}));
		if(logo instanceof HasClickHandlers){
			registerHandler(((HasClickHandlers) logo).addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(logoClickedLogic != null && logoClickEnabled){
						logoClickedLogic.doLogic();
					}
				}
			}));
		}
	}
	
	@Override
	public void enableAliasMode() {		
		addStyleName(ResourceHelper.getRes().style().inAliasMode());
	}
	
	@Override
	public void disableAliasMode() {
		removeStyleName(ResourceHelper.getRes().style().inAliasMode());		
	}
	
	@Override
	public void setMenuIconClickedLogic(OnClickedLogic logic) {
		menuIconClickedLogic = logic;
	}

	@Override
	public void setLanguageSelectorVisible(boolean visible) {
		
	}

	@Override
	public void setLogoClickedLogic(OnClickedLogic logic) {
		this.logoClickedLogic = logic;
	}
	
	@Override
	public void toggleMenuIcon(boolean isNavigationDrawerOpen) {
		if (isNavigationDrawerOpen) {
			menuIcon.addStyleName(ResourceHelper.getRes().style().backIcon());
			menuIcon.removeStyleName(ResourceHelper.getRes().style().menuIcon());
		} 
		
		else {
			menuIcon.removeStyleName(ResourceHelper.getRes().style().backIcon());
			menuIcon.addStyleName(ResourceHelper.getRes().style().menuIcon());
		}
	}

	@Override
	public void onLogin() {
		menuIcon.setVisible(true);
		logoClickEnabled = true;
	}

	@Override
	public void onLogout() {
		menuIcon.setVisible(false);
		logoClickEnabled = false;
	}
}
