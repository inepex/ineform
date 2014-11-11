package com.inepex.ineFrame.client.navigation.defaults;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.GreenScrollPanel;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.shared.IneformAsyncCallback;

@Singleton
public class IneMenuView extends HandlerAwareFlowPanel {
	
	private FlexTable usertable = new FlexTable();
	private FlowPanel appsPanel = new FlowPanel();
	private FlowPanel helpAndSettingsPanel = new FlowPanel();
	private Label appsLbl = new Label(IneFrameI18n.inemenu_apps());
	private Label helpAndSettingsLbl = new Label(IneFrameI18n.inemenu_helpsettings());
	private FlexTable appsTbl = new FlexTable();
	private FlexTable helpAndSettingsTable = new FlexTable();
	private final GreenScrollPanel scrollPanel = new GreenScrollPanel();
	private FlowPanel scrollContent = new FlowPanel();
	
	private Map<Integer, String> appIdMap = new HashMap<>();
	private IneformAsyncCallback<Void> logoutHandler;
	private IneformAsyncCallback<Void> accountSettingsCbk;
	private IneformAsyncCallback<Void> helpCbk;
	private IneformAsyncCallback<String> appCbk;
	
	@Inject
	public IneMenuView() {
		buildStructure();
		setStyles();
	}
	
	public void addApp(ImageResource icon, String appName, String appId){
		appsPanel.setVisible(true);
		int row = appsTbl.getRowCount();
		appIdMap.put(row, appId);
		appsTbl.setWidget(row, 0, new Image(icon));
		appsTbl.setText(row, 1, appName);
		
		appsTbl.getRowFormatter().setStyleName(row, Res.INST.get().ineMenuStyle().menuElementRow());
		appsTbl.getFlexCellFormatter().setStyleName(row, 0, Res.INST.get().ineMenuStyle().menuElementIcon());	
	}
	
	public void setUser(String userName, String email){
		if (userName == null && email == null){
			usertable.setVisible(false);
			usertable.setText(0, 1, "");
			usertable.setText(1, 0, "");
		} else {
			usertable.setVisible(true);
			usertable.setText(0, 1, userName);
			usertable.setText(1, 0, email);
		}
	}
	
	public void setLogoutCbk(IneformAsyncCallback<Void> logoutCbk){
		this.logoutHandler = logoutCbk;
	}
	
	public void setAccountSettingsCbk(IneformAsyncCallback<Void> accountSettingsCbk){
		this.accountSettingsCbk = accountSettingsCbk;
	}
	
	public void setHelpCallback(IneformAsyncCallback<Void> helpCbk){
		this.helpCbk = helpCbk;
	}
	
	public void setAppCbk(IneformAsyncCallback<String> appCbk){
		this.appCbk = appCbk;
	}

	private void setStyles() {
		getElement().getStyle().setHeight(100.0, Unit.PCT);
		scrollPanel.asWidget().addStyleName(Res.INST.get().ineMenuStyle().ineMenuScrollPanel());
		scrollContent.addStyleName(Res.INST.get().ineMenuStyle().ineMenu());
		usertable.setStyleName(Res.INST.get().ineMenuStyle().userProfile());
		appsLbl.setStyleName(Res.INST.get().ineMenuStyle().menuSeparatorLbl());
		helpAndSettingsLbl.setStyleName(Res.INST.get().ineMenuStyle().menuSeparatorLbl());
		appsPanel.setStyleName(Res.INST.get().ineMenuStyle().menuSeparatorPanel());
		helpAndSettingsPanel.setStyleName(Res.INST.get().ineMenuStyle().menuSeparatorPanel());
		helpAndSettingsTable.setStyleName(Res.INST.get().ineMenuStyle().menuElementTable());
		appsTbl.setStyleName(Res.INST.get().ineMenuStyle().menuElementTable());
		
		usertable.setCellSpacing(0);
		usertable.setCellPadding(0);
		appsTbl.setCellSpacing(0);
		appsTbl.setCellPadding(0);
		helpAndSettingsTable.setCellSpacing(0);
		helpAndSettingsTable.setCellPadding(0);
	}

	private void buildStructure(){
		add(scrollPanel);
		appsPanel.add(appsLbl);
		appsPanel.setVisible(false);
		scrollPanel.setScrollContent(scrollContent);
		scrollContent.add(usertable);
		scrollContent.add(appsPanel);
		scrollContent.add(appsTbl);
		scrollContent.add(helpAndSettingsPanel);
		scrollContent.add(helpAndSettingsTable);		
		helpAndSettingsPanel.add(helpAndSettingsLbl);
		helpAndSettingsTable.setWidget(0, 0, new Image(Res.INST.get().drawerHelp()));
		helpAndSettingsTable.setText(0, 1, IneFrameI18n.inemenu_help());
		helpAndSettingsTable.setWidget(1, 0, new Image(Res.INST.get().drawerAccountSettings()));
		helpAndSettingsTable.setText(1, 1, IneFrameI18n.inemenu_settings());
		helpAndSettingsTable.setWidget(2, 0, new Image(Res.INST.get().drawerLogout()));
		helpAndSettingsTable.setText(2, 1, IneFrameI18n.inemenu_logout());
		
		helpAndSettingsTable.getRowFormatter().setStyleName(0, Res.INST.get().ineMenuStyle().menuElementRow());
		helpAndSettingsTable.getRowFormatter().setStyleName(1, Res.INST.get().ineMenuStyle().menuElementRow());
		helpAndSettingsTable.getRowFormatter().setStyleName(2, Res.INST.get().ineMenuStyle().menuElementRow());
		
		helpAndSettingsTable.getFlexCellFormatter().setStyleName(0, 0, Res.INST.get().ineMenuStyle().menuElementIcon());
		helpAndSettingsTable.getFlexCellFormatter().setStyleName(1, 0, Res.INST.get().ineMenuStyle().menuElementIcon());
		helpAndSettingsTable.getFlexCellFormatter().setStyleName(2, 0, Res.INST.get().ineMenuStyle().menuElementIcon());
		
		buildUserPanel();
	}
	private void buildUserPanel(){
		Image userImg = new Image(Res.INST.get().drawerProfile());
		userImg.setStyleName(Res.INST.get().ineMenuStyle().userProfileImg());
		usertable.getFlexCellFormatter().setRowSpan(0, 0, 2);
		usertable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
		usertable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		usertable.getFlexCellFormatter().setStyleName(0, 1, Res.INST.get().ineMenuStyle().userProfileName());
		usertable.getFlexCellFormatter().setStyleName(1, 0, Res.INST.get().ineMenuStyle().userProfileEmail());
		usertable.setWidget(0, 0, userImg);
	}
	
	private void registerHandlers(){
		registerHandler(appsTbl.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Cell cell = appsTbl.getCellForEvent(event);
				int rowIndex = cell.getRowIndex();
				String appId = appIdMap.get(rowIndex);
				if(appId != null){
					appCbk.onResponse(appId);
				}
			}
		}));
		registerHandler(helpAndSettingsTable.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Cell cell = helpAndSettingsTable.getCellForEvent(event);
				int rowIndex = cell.getRowIndex();
				if(rowIndex == 0 && helpCbk != null){
					helpCbk.onResponse(null);
				}else if(rowIndex == 1 && accountSettingsCbk != null){
					accountSettingsCbk.onResponse(null);
				}else if(rowIndex == 2 && logoutHandler != null){
					logoutHandler.onResponse(null);
				}
			}
		}));
	}
	
	@Override
	protected void onLoad() {
		registerHandlers();
		super.onLoad();
	}
	public void enableAliasMode(String name, String email){
		helpAndSettingsTable.setWidget(2, 0, new Image(Res.INST.get().drawerLeaveAlias()));
		helpAndSettingsTable.setText(2, 1, IneFrameI18n.inemenu_leavealias());
		usertable.setText(0, 1, name);
		usertable.setText(1, 0, email);
		usertable.addStyleName(Res.INST.get().ineMenuStyle().inAliasMode());
	}
	public void disableAliasMode(String name, String email){
		helpAndSettingsTable.setWidget(2, 0, new Image(Res.INST.get().drawerLogout()));
		helpAndSettingsTable.setText(2, 1, IneFrameI18n.inemenu_logout());
		usertable.setText(0, 1, name);
		usertable.setText(1, 0, email);
		usertable.removeStyleName(Res.INST.get().ineMenuStyle().inAliasMode());
	}
	public void clearApps() {
		appsTbl.removeAllRows();
		appsPanel.setVisible(false);
	}
	
	public void onDisplayed(){
		scrollPanel.scrollToTop();
	}
	
	public void onHidden(){
		
	}
}
