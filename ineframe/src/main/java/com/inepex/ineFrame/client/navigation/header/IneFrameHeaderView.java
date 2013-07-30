package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.navigation.header.widget.ClickLabel;
import com.inepex.ineFrame.client.navigation.header.widget.SettingsPopup;
import com.inepex.ineFrame.client.widget.ClickableFlowPanel;

@Singleton
public class IneFrameHeaderView extends HandlerAwareFlowPanel implements IneFrameHeader.View {
	
	private Widget logo;
	private HTML userName;
	private ClickableFlowPanel settingsPanel;
	private Image settingsImg;
	private SettingsPopup popup;
	private OnClickedLogic logoClickedLogic;
	private OnClickedLogic settingsButtonLogic;
	private OnClickedLogic usernameClickedLogic;
	
	private HTML login = new HTML(IneFrameI18n.LOGIN());
	private OnClickedLogic loginClickLogic;
	
	@Inject
	IneFrameHeaderView(HeaderViewLogo logoCreator) {
		setStyleName(ResourceHelper.getRes().style().header());
		
		logo = logoCreator.createLogo();
		add(logo);
		
		settingsImg = new Image(ResourceHelper.getRes().settings());
		settingsImg.setStyleName(ResourceHelper.getRes().style().settingImg());
		settingsPanel = new ClickableFlowPanel();
		settingsPanel.add(settingsImg);
		settingsPanel.setStyleName(ResourceHelper.getRes().style().settingPanel());
		add(settingsPanel);
		
		
		userName=new HTML();
		userName.setStyleName(ResourceHelper.getRes().style().settingsUserName());
		add(userName);
		
		login.setVisible(false);
		login.setStyleName(ResourceHelper.getRes().style().settingsUserName());
		add(login);
		
		popup=new SettingsPopup();
	}

	@Override
	public void setUserName(String username, boolean showLoginLinkWhenLoggedOut) {
		if(username==null) {
			userName.setVisible(false);
			userName.setHTML("");
			if (showLoginLinkWhenLoggedOut) login.setVisible(true);
		} else {
			userName.setVisible(true);
			userName.setHTML(username);
			login.setVisible(false);
		}
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
			
		registerHandler(settingsPanel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(settingsButtonLogic!=null)
					settingsButtonLogic.doLogic();
			}
		}));
		
		registerHandler(userName.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (usernameClickedLogic != null) {
					usernameClickedLogic.doLogic();
				}
			}
		}));
		
		if(logo instanceof HasClickHandlers) {
			registerHandler(((HasClickHandlers)logo).addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (logoClickedLogic != null) {
						logoClickedLogic.doLogic();
					}
				}
			}));
		}
		
		registerHandler(login.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if (loginClickLogic != null) {
						loginClickLogic.doLogic();
					}
				}
		}));
		
	}

	@Override
	public void setSettingsButtonVisible(boolean visible) {
		settingsPanel.setVisible(visible);
	}

	@Override
	public void setSettingsButtonLogic(OnClickedLogic logic) {
		this.settingsButtonLogic=logic;
	}

	@Override
	public void clearSettingsPopup() {
		popup.clearClickLabels();
	}

	@Override
	public void addToSettingsPopup(String name, OnClickedLogic logic) {
		popup.addClickLabel(new ClickLabel(name, logic));
	}

	@Override
	public void showSettingsPopup() {
		popup.showRelativeTo(settingsPanel);
	}

	@Override
	public void hideSettingsPopup() {
		popup.hide();
	}

	@Override
	public boolean isSettingsPopupShowing() {
		return popup.isShowing();
	}
	
	@Override
	public void setUserNameClickedLogic(OnClickedLogic logic) {
		usernameClickedLogic = logic;
	}

	@Override
	public void setLogoNameClickedLogic(OnClickedLogic logic) {
		logoClickedLogic = logic;
	}

	@Override
	public void setLanguageSelectorVisible(boolean visible) {
	}
	
	@Override
	public void setLoginClickLogic(OnClickedLogic logic){
		this.loginClickLogic = logic;
	}
	
}
