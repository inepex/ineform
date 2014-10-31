package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.auth.UserLoggedInEvent;
import com.inepex.ineFrame.client.auth.UserLoggedOutEvent;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanel;
import com.inepex.ineFrame.shared.IneformAsyncCallback;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

public class IneMenu implements NavigationDrawer {

	private IneMenuView view;
	private AuthManager authManager;
	private Provider<MessagePanel> messagePanel;

	@Inject
	public IneMenu(
			IneMenuView view, 
			final EventBus eventBus,
			final AuthManager authManager,
			final Provider<MessagePanel> messagePanel) {
		this.view = view;
		this.authManager = authManager;
		this.messagePanel = messagePanel;
		
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
		
		//for test only
		view.setHelpHandler(new IneformAsyncCallback<Void>() {
			
			@Override
			public void onResponse(Void response) {
				messagePanel.get().showMessage("Helló új design és animációk", false);
			}
		});
		
		//for test only
		view.setLogoutHandler(new IneformAsyncCallback<Void>() {

			@Override
			public void onResponse(Void response) {
				authManager.doLogout(new AuthActionCallback() {
					@Override
					public void onAuthCheckDone(AuthStatusResultBase result) {
						eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
					}
				});
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
