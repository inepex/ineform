package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class IneFrameHeader extends HandlerAwareFlowPanel {
	
//	@Inject
	private AuthManager<AuthStatusResultBase> authManager;
	
	@Inject
	PlaceHierarchyProvider placeHierarchyProvider;
	
	@Inject
	Provider<SettingsPopup> popup;
	
	private boolean inited=false;
	private HTML userName;
	private Image settingsImg;
	
	
	private void init() {
		inited=true;
		
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
	}

	public void refresh() {
		//user name
		if(authManager!=null && authManager.isUserLoggedIn()) {
			userName.setVisible(true);
			userName.setHTML(authManager.getLastAuthStatusResult().getFirstName()
					+"&nbps;"+authManager.getLastAuthStatusResult().getLastName());
		} else {
			userName.setVisible(false);
		}
		
		//settings img
		Node<InePlace> settingsNode = placeHierarchyProvider.getPlaceRoot().findNodeById(NavigationProperties.SETTINGS);
		if(settingsNode!=null && settingsNode.getChildren()!=null && settingsNode.getChildren().size()>0) {
			settingsImg.setVisible(true);
		} else {
			settingsImg.setVisible(false);
		}
		
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		
		if(!inited) { 
			init();
		}
		
		registerHandler(settingsImg.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				popup.get().showRelativeTo(settingsImg);
			}
		}));
	}

}
