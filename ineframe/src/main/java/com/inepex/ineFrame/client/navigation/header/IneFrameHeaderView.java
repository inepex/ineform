package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.navigation.header.widget.ClickLabel;
import com.inepex.ineFrame.client.navigation.header.widget.SettingsPopup;

@Singleton
public class IneFrameHeaderView extends HandlerAwareFlowPanel implements IneFrameHeader.View {
	
	private HTML userName;
	private Image settingsImg;
	private SettingsPopup popup;
	private OnClickedLogic settingsButtonLogic;
	private OnClickedLogic usernameClickedLogic;
	
	@Inject
	IneFrameHeaderView() {
		setStyleName(ResourceHelper.getRes().style().header());
		
		Image logo = new Image(ResourceHelper.getRes().logo());
		logo.setStyleName(ResourceHelper.getRes().style().logo());
		add(logo);
		
		settingsImg = new Image(ResourceHelper.getRes().settings());
		settingsImg.getElement().getStyle().setFloat(Float.RIGHT);
		settingsImg.getElement().getStyle().setCursor(Cursor.POINTER);
		add(settingsImg);
		
		userName=new HTML();
		userName.setStyleName(ResourceHelper.getRes().style().settingsUserName());
		add(userName);
		
		popup=new SettingsPopup();
	}

	@Override
	public void setUserName(String username) {
		if(username==null) {
			userName.setVisible(false);
			userName.setHTML("");
		} else {
			userName.setVisible(true);
			userName.setHTML(username);
		}
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
			
		registerHandler(settingsImg.addClickHandler(new ClickHandler() {
			
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
		
	}

	@Override
	public void setSettingsButtonVisible(boolean visible) {
		settingsImg.setVisible(visible);
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
		popup.showRelativeTo(settingsImg);
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
	public void beforeUserNameClickEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUserNameClickEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getUserNameClickEffectXPosition() {
		// TODO Auto-generated method stub
		
	}
}
