package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.MasterPage;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;

@Singleton
public class IneFrameHeader {
	
	public static interface View {
		/**
		 * @param username - username as html or null to hide
		 */
		
		public void setLanguageSelectorVisible(boolean visible);
		
		public void setMenuIconClickedLogic(OnClickedLogic logic);
		
		public void setLogoClickedLogic(OnClickedLogic logic);
		
		public void setApp(ImageResource img, String appName);
		
		public void clearApp();
		
		public void enableAliasMode();
		
		public void disableAliasMode();
		
		public IsWidget asWidget();
		
		public void hideMenuIcon();
		
		public void showMenuIcon();
		
		public void toggleMenuIcon(boolean isNavigationDrawerOpen);
		
	}
	
	private final Provider<View> viewProv;
	private View view;

	private OnClickedLogic menuIconClickLogic = new OnClickedLogic() {
		
		@Override
		public void doLogic() {
			masterPageView.get().toggleNavigationDrawer();
			getOrCreateView().toggleMenuIcon(masterPageView.get().isNavigationDrawerOpen());
		}
	};
	
	private OnClickedLogic logoClickLogic = new OnClickedLogic() {
		
		@Override
		public void doLogic() {
			masterPageView.get().toggleNavigationDrawer();
			getOrCreateView().toggleMenuIcon(masterPageView.get().isNavigationDrawerOpen());
		}
	};
	
	private Provider<MasterPage.View> masterPageView;
	
	@Inject
	public IneFrameHeader(AuthManager authManager,
						  Provider<View> view, 
						  Provider<MasterPage.View> masterPageView) {
		super();
		this.viewProv = view;
		this.masterPageView = masterPageView;
	}
	
	public void refresh() {
		boolean showLangSelector = true;
		getOrCreateView().setLanguageSelectorVisible(showLangSelector);
		getOrCreateView().setMenuIconClickedLogic(menuIconClickLogic);
		getOrCreateView().setLogoClickedLogic(logoClickLogic);
	
	}
	
	public void setMenuIconClickedLogic(OnClickedLogic logic) {
		this.menuIconClickLogic = logic;
	}
	
	public void setLogoClickedLogic(OnClickedLogic logic) {
		this.logoClickLogic = logic;
	}
	
	private View getOrCreateView(){
		if (view == null){ 
			view = viewProv.get();
		}
		return view;
	}
}
