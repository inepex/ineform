package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.auth.UserLoggedInEvent;
import com.inepex.ineFrame.client.auth.UserLoggedOutEvent;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;

public class IneMenu implements NavigationDrawer {

	private IneMenuView view;
	private AuthManager authManager;

	@Inject
	public IneMenu(
			IneMenuView view, 
			EventBus eventBus,
			AuthManager authManager) {
		this.view = view;
		this.authManager = authManager;
		
		refresh();
		
		eventBus.addHandler(UserLoggedInEvent.TYPE, new UserLoggedInEvent.Handler() {
			
			@Override
			public void onUserLoggedIn() {
				refresh();
			}
		});
		
		eventBus.addHandler(UserLoggedOutEvent.TYPE, new UserLoggedOutEvent.Handler() {

			@Override
			public void onUserLoggedOut() {
				refresh();
			}	
		});
	}

	@Override
	public Widget asWidget() {
		return view;
	}
	
	private void refresh(){
		if(!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn()) {
			view.setUser(authManager.getLastAuthStatusResult().getDisplayName(), 
					authManager.getLastAuthStatusResult().getUserEmail());
		} else {
			view.setUser(null, null);
		}
	}
	
}
