package com.inepex.ineFrame.client.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.inepex.ineFrame.shared.IneformAsyncCallback;

public interface NavigationDrawer extends IsWidget {
	public void disableAliasMode(IneformAsyncCallback<Void> callback, String name, String email);
	public void enableAliasMode(IneformAsyncCallback<Void> callback, String name, String email);
	public void onDisplayed();	
	public void onHidden();		
	
}
