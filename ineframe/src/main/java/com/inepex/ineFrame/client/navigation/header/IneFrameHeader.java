package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
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
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPage;
import com.inepex.ineFrame.client.navigation.defaults.DefaultIneFrameMasterPageView;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class IneFrameHeader implements PlaceRequestHandler {
	
	public static interface View {
		/**
		 * @param username - username as html or null to hide
		 */
		public void setUserName(String username, boolean showLoginLinkWhenLoggedOut);
		
		public void setSettingsButtonVisible(boolean visible);
		public void setSettingsButtonLogic(OnClickedLogic logic);
		
		public void setLanguageSelectorVisible(boolean visible);
		
		public void clearSettingsPopup();
		public void addToSettingsPopup(String name, OnClickedLogic logic);
		public void showSettingsPopup();
		public void hideSettingsPopup();
		public boolean isSettingsPopupShowing();
		
		public void setUserNameClickedLogic(OnClickedLogic logic);
		
		public void setLogoNameClickedLogic(OnClickedLogic logic);
		
		public void setLoginClickLogic(OnClickedLogic logic);
		
		public IsWidget asWidget();
	}

	private class DefaultSettingOnClicked implements OnClickedLogic {
		@Override
		public void doLogic() {
			if(getOrCreateView().isSettingsPopupShowing()) {
				getOrCreateView().hideSettingsPopup();
			} else {
				getOrCreateView().showSettingsPopup();
			}
		}
	}
	
	private final AuthManager authManager;
	private final PlaceHierarchyProvider placeHierarchyProvider;
	private final EventBus eventBus;
	
	private final Provider<View> viewProv;
	private View view;
	
	
	private OnClickedLogic settingsClickLogic;
	private OnClickedLogic logoClickLogic = new OnClickedLogic() {
		
		@Override
		public void doLogic() {
//			eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
			masterPageView.toggleNavigationDrawer();
		}
	};
	
	private boolean showLoginLinkWhenLoggedOut = false;
	private com.inepex.ineFrame.client.navigation.MasterPage.View masterPageView;
	
	@Inject
	public IneFrameHeader(AuthManager authManager,
			PlaceHierarchyProvider placeHierarchyProvider,
			Provider<View> view, EventBus eventBus, DefaultIneFrameMasterPage.View masterPageView) {
		super();
		this.authManager = authManager;
		this.placeHierarchyProvider = placeHierarchyProvider;
		this.viewProv = view;
		this.eventBus=eventBus;
		this.masterPageView = masterPageView;
		
		eventBus.addHandler(PlaceRequestEvent.TYPE, this);
		
	}
	
	public IneFrameHeader setSettingsClickLogic(OnClickedLogic settingsClickLogic) {
		this.settingsClickLogic = settingsClickLogic;
		return this;
	}

	@Override
	public void onPlaceRequest(PlaceRequestEvent e) {
		getOrCreateView().hideSettingsPopup();
	}

	public void refresh() {
		if (settingsClickLogic == null) settingsClickLogic = new DefaultSettingOnClicked();
		getOrCreateView().setSettingsButtonLogic(settingsClickLogic);
		getOrCreateView().setUserNameClickedLogic(settingsClickLogic);
		
		if(!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn()) {
			getOrCreateView().setUserName(authManager.getLastAuthStatusResult().getDisplayName(), showLoginLinkWhenLoggedOut);
		} else {
			getOrCreateView().setUserName(null, showLoginLinkWhenLoggedOut);
		}
		
		boolean showSettings = false;
		boolean showLangSelector = true;
		getOrCreateView().clearSettingsPopup();
		
		Node<InePlace> settingsPlaceRoot = placeHierarchyProvider.getPlaceRoot()
										   .findNodeById(NavigationProperties.SETTINGS);
		
		if(settingsPlaceRoot != null) {
			for(Node<InePlace> placeNode : settingsPlaceRoot.getChildren()) {
				
				if(placeNode.getNodeElement().getMenuName()==null || placeNode.getNodeElement().isOnlyVisibleWhenActive())
					continue;
				
				if(!(authManager instanceof NoAuthManager) &&
						!authManager.doUserHaveAnyOfRoles(placeNode.getNodeElement().getRolesAllowedInArray()))
					continue;
			
				showSettings=true;
				final String hierarchicalID = placeNode.getHierarchicalId();
				getOrCreateView().addToSettingsPopup(placeNode.getNodeElement().getMenuName(), new OnClickedLogic() {
					
					@Override
					public void doLogic() {
						eventBus.fireEvent(new PlaceRequestEvent(hierarchicalID));
					}
				});
			}
		}
		
		if((!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn())) {
			showSettings = true;
			showLangSelector = false;
			getOrCreateView().addToSettingsPopup(IneFrameI18n.LOGOUT(), new OnClickedLogic() {
				
				@Override
				public void doLogic() {
					authManager.doLogout(new AuthActionCallback() {
						@Override
						public void onAuthCheckDone(AuthStatusResultBase result) {
							eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
						}
					});
				}
			});
		}
		getOrCreateView().setLanguageSelectorVisible(showLangSelector);
		getOrCreateView().setSettingsButtonVisible(showSettings);
		getOrCreateView().setLogoNameClickedLogic(logoClickLogic);
		if (showLoginLinkWhenLoggedOut){
			getOrCreateView().setLoginClickLogic(new OnClickedLogic() {
				
				@Override
				public void doLogic() {
					eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.loginPlace));
				}
			});
		}
	}
	
	public void setLogoNameClickedLogic(OnClickedLogic logic) {
		this.logoClickLogic = logic;		
	}
	
	private View getOrCreateView(){
		if (view == null){ 
			view = viewProv.get();
		}
		
		return view;
	}
	
	public void setShowLoginLinkWhenLoggedOut(boolean showLoginLinkWhenLoggedOut) {
		this.showLoginLinkWhenLoggedOut = showLoginLinkWhenLoggedOut;
	}
}
