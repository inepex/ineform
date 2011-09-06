package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.PlaceRequestHandler;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class IneFrameHeader implements PlaceRequestHandler {
	
	public static interface View {
		/**
		 * @param username - username as html or null to hide
		 */
		public void setUserName(String username);
		
		public void setSettingsButtonVisible(boolean visible);
		public void setSettingsButtonLogic(OnClickedLogic logic);
		
		public void clearSettingsPopup();
		public void addToSettingsPopup(String name, OnClickedLogic logic);
		public void showSettingsPopup();
		public void hideSettingsPopup();
		public boolean isSettingsPopupShowing();
		
		public void setUserNameClickedLogic(OnClickedLogic logic);
	}
	
	private final AuthManager authManager;
	private final PlaceHierarchyProvider placeHierarchyProvider;
	private final EventBus eventBus;
	
	private final View view;
	
	@Inject
	public IneFrameHeader(AuthManager authManager,
			PlaceHierarchyProvider placeHierarchyProvider,
			View view, EventBus eventBus) {
		super();
		this.authManager = authManager;
		this.placeHierarchyProvider = placeHierarchyProvider;
		this.view = view;
		this.eventBus=eventBus;
		
		init();
	}
	
	private void init() {
		eventBus.addHandler(PlaceRequestEvent.TYPE, this);
		
		view.setSettingsButtonLogic(new OnClickedLogic() {
			
			@Override
			public void doLogic() {
				if(view.isSettingsPopupShowing()) {
					view.hideSettingsPopup();
				} else {
					view.showSettingsPopup();
				}
			}
		});
	}

	@Override
	public void onPlaceRequest(PlaceRequestEvent e) {
		view.hideSettingsPopup();
	}

	public void refresh() {
		if(!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn()) {
			view.setUserName(authManager.getLastAuthStatusResult().getDisplayName());
		} else {
			view.setUserName(null);
		}
		
		boolean showSettings=false;
		view.clearSettingsPopup();
		
		if(placeHierarchyProvider.getPlaceRoot().findNodeById(NavigationProperties.SETTINGS)!=null) {
			for(Node<InePlace> placeNode : placeHierarchyProvider.getPlaceRoot().findNodeById(NavigationProperties.SETTINGS).getChildren()) {
				
				if(placeNode.getNodeElement().getMenuName()==null || placeNode.getNodeElement().isOnlyVisibleWhenActive())
					continue;
				
				if(!(authManager instanceof NoAuthManager) &&
						!authManager.doUserHaveAnyOfRoles(placeNode.getNodeElement().getRolesAllowedInArray()))
					continue;
			
				showSettings=true;
				final String hierarchicalID = placeNode.getHierarchicalId();
				view.addToSettingsPopup(placeNode.getNodeElement().getMenuName(), new OnClickedLogic() {
					
					@Override
					public void doLogic() {
						eventBus.fireEvent(new PlaceRequestEvent(hierarchicalID));
					}
				});
			}
		}
		
		if((!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn())) {
			showSettings=true;
			
			//TODO get from i18n
			view.addToSettingsPopup(IneFrameI18n.LOGOUT(), new OnClickedLogic() {
				
				@Override
				public void doLogic() {
					authManager.doLogout(new AuthActionCallback() {
						
						@Override
						public void onAuthCheckDone() {
							eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
						}
					});
				}
			});
		}
		
		view.setSettingsButtonVisible(showSettings);
	}
	
	public void setUserNameClickedLogic(OnClickedLogic logic){
		view.setUserNameClickedLogic(logic);
	}
}
