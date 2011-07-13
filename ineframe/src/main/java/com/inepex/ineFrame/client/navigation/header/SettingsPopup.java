package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.PlaceRequestHandler;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class SettingsPopup extends DialogBox implements PlaceRequestHandler {

	private final PlaceHierarchyProvider hierarchyProvider;
	private final EventBus eventBus;
	private final AuthManager authManager;
	
	boolean inited = false;
	VerticalPanel panel;
	
	LogoutButton logoutButton;
	
	@Inject
	SettingsPopup(PlaceHierarchyProvider hierarchyProvider, EventBus eventBus, AuthManager authManager) {
		super(false, false);
		this.hierarchyProvider=hierarchyProvider;
		this.eventBus=eventBus;
		this.authManager=authManager;
		
		eventBus.addHandler(PlaceRequestEvent.TYPE, this);
		
		panel = new VerticalPanel();
		logoutButton = new LogoutButton();
		add(panel);
	}
	
	@Override
	public void show() {
		panel.clear();
		
		for(Node<InePlace> placeNode : hierarchyProvider.getPlaceRoot().findNodeById(NavigationProperties.SETTINGS).getChildren()) {
			if(!(authManager instanceof NoAuthManager) &&
					!authManager.doUserHaveAnyOfRoles(placeNode.getNodeElement().getRolesAllowedInArray()))
				continue;
		
			panel.add(new PlaceButton(placeNode.getHierarchicalId(), placeNode.getNodeElement()));
		}
		
		if((!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn()))
			panel.add(logoutButton);
		
		super.show();
	}
	
	@Override
	public void onPlaceRequest(PlaceRequestEvent e) {
		hide(true);
	}
	
	private class LogoutButton extends HandlerAwareComposite {
		
		private final Label label;
		
		public LogoutButton() {
			
			//TODO get from i18n
			label=new Label("Log out");
			label.getElement().getStyle().setCursor(Cursor.POINTER);
			initWidget(label);
		}
		
		@Override
		protected void onAttach() {
			super.onAttach();
			if(!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn()) {
				registerHandler(label.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						authManager.doLogout(new AuthActionCallback() {
							
							@Override
							public void onAuthCheckDone() {
								eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
							}
						});
					}
				}));
			}
		}
		
	}
	
	private class PlaceButton extends HandlerAwareComposite {
		
		private final Label label;
		private final String hierarchicalId;
		
		public PlaceButton(String hierarchicalId, InePlace nodeElement) {
			this.hierarchicalId=hierarchicalId;
			
			label=new Label(nodeElement.getMenuName());
			label.getElement().getStyle().setCursor(Cursor.POINTER);
			initWidget(label);
		}
		
		@Override
		protected void onAttach() {
			super.onAttach();
			
			registerHandler(label.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new PlaceRequestEvent(hierarchicalId));
				}
			}));
		}
		
	}
}
